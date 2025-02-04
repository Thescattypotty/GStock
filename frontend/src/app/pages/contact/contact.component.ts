import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { ListboxModule } from 'primeng/listbox';
import { RippleModule } from 'primeng/ripple';
import { SelectModule } from 'primeng/select';
import { Table, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { DrawerModule } from 'primeng/drawer';
import { ContactService } from '../../services/contact/contact.service';
import { ContactResponse } from '../../models/response/contact-response';
import { CommonModule } from '@angular/common';
import { ContactRequest } from '../../models/request/contact-request';
import { CompanyResponse } from '../../models/response/company-response';
import { CompanyService } from '../../services/company/company.service';
import { CardModule } from 'primeng/card';

interface Column {
    field: string;
    header: string;
    customExportHeader?: string;
}

interface ExportColumn {
    title: string;
    dataKey: string;
}

@Component({
    selector: 'app-contact',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FormsModule,
        ButtonModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        InputTextModule,
        SelectModule,
        InputNumberModule,
        DialogModule,
        TagModule,
        InputIconModule,
        IconFieldModule,
        ConfirmDialogModule,
        MultiSelectModule,
        TooltipModule,
        ListboxModule,
        DrawerModule,
        CardModule
        ],
    templateUrl: './contact.component.html',
    providers: [ConfirmationService]
})
export class ContactComponent implements OnInit {
    
    contactDialog: boolean = false;
    contacts = signal<ContactResponse[]>([]);
    companies: CompanyResponse[] = [];

    contact!: ContactRequest;
    contactShow!: ContactResponse;
    contactId!: string | null;

    visibility: boolean = false;

    selectedContacts!: ContactResponse[] | null;
    
    submitted: boolean = false;
        
    @ViewChild('dt') dt!: Table;
    exportColumns!: ExportColumn[];

    cols!: Column[];
    
    constructor(
        private messageService: MessageService,
        private contactService: ContactService,
        private companyService: CompanyService,
        private confirmationService: ConfirmationService
    ) {

    }

    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit(): void {
        this.loadContacts();
        this.loadCompanies();
        this.initColsAndExportCols();
    }

    loadContacts() {
        this.contactService.getAllContacts().subscribe({
            next: (response) => {
                this.contacts.set(response);
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while loading contacts' });
            }
        });
    }
    loadCompanies(){
        this.companyService.getAllCompanies().subscribe({
            next: (response) => {
                this.companies = response;
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while loading companies' });
            }
        });
    }

    initColsAndExportCols(): void {
        this.cols = [
            { field: 'id', header: 'ID' , customExportHeader: 'ID'},
            { field: 'fullName', header: 'Full name' , customExportHeader: 'Full name'},
            { field: 'imageUrl', header: 'Image URL' , customExportHeader: 'Image URL'},
            { field: 'email', header: 'Email' , customExportHeader: 'Email'},
            { field: 'title', header: 'Title' , customExportHeader: 'Title'},
            { field: 'phone', header: 'Phone' , customExportHeader: 'Phone'},
            { field: 'isCustomer', header: 'Is customer' , customExportHeader: 'Is customer'},
            { field: 'companyResponse', header: 'Company' , customExportHeader: 'Company'},
            { field: 'createdBy', header: 'Created by' , customExportHeader: 'Created by'},
            { field: 'updatedBy', header: 'Updated by' , customExportHeader: 'Updated by'},
            { field: 'createdAt', header: 'Created at' , customExportHeader: 'Created at'},
            { field: 'updatedAt', header: 'Updated at' , customExportHeader: 'Updated at'}
        ];

        this.exportColumns = this.cols.map((col) => ({ title: col.header, dataKey: col.field }));
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }
    openVisibility(contact: ContactResponse){
        this.contactShow = contact;
        this.visibility = true;
    }

    openNew(){
        this.contact = {
            fullName: '',
            imageUrl: '',
            email: '',
            title: '',
            phone: '',
            companyId: ''
        };
        this.submitted = false;
        this.contactDialog = true;
    }

    hideDialog(){
        this.contactDialog = false;
        this.submitted = false;
        this.contactId = null;
    }
    editContact(contact: ContactResponse){
        this.contact = {
            fullName: contact.fullName,
            imageUrl: contact.imageUrl,
            email: contact.email,
            title: contact.title,
            phone: contact.phone,
            companyId: contact.companyResponse.id
        };
        this.contactId = contact.id;
        this.contactDialog = true;
    }

    deleteContact(contact: ContactResponse){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + contact.fullName + '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.contactService.deleteContact(contact.id).subscribe({
                    next: () => {
                        this.loadContacts();
                        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Contact deleted', life: 3000 });
                    },
                    error: () => {
                        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while deleting contact' });
                    }
                });
            }
        });
    }

    deleteSelectedContacts(){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete the selected contacts?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.selectedContacts?.forEach((contact) => {
                    this.contactService.deleteContact(contact.id).subscribe({
                        next: () => {
                            this.loadContacts();
                            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Contacts deleted', life: 3000 });
                        },
                        error: () => {
                            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while deleting contacts' });
                        }
                    });
                });
            }
        });
    }

    findIndexById(id: string): string {
        let index = -1;
        for (let i = 0; i < this.contacts.length; i++) {
            if (this.contacts()[i].id === id) {
                index = i;
                break;
            }
        }
        return index + '';
    }

    onCall(phone: string): void {
        window.location.href = 'tel:' + phone;
    }

    onEmail(email: string): void {
        window.location.href = 'mailto:' + email;
    }
    
    updateContact(){
        this.submitted = true;
        if(this.contactId === null){
            return;
        }
        this.contactService.updateContact(this.contactId, this.contact).subscribe({
            next: () => {
                this.loadContacts();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Contact updated', life: 3000 });
                this.contactDialog = false;
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while updating contact' });
            }
        });
        this.contactId = null;
    }

    saveContact(){
        this.submitted = true;
        this.contactService.createContact(this.contact).subscribe({
            next: () => {
                this.loadContacts();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Contact created', life: 3000 });
                this.contactDialog = false;
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while creating contact' });
            }
        });
    }
}

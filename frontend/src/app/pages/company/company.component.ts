import { CommonModule } from '@angular/common';
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
import { CompanyResponse } from '../../models/response/company-response';
import { CompanyRequest } from '../../models/request/company-request';
import { CompanyService } from '../../services/company/company.service';
import { ECompanySize } from '../../models/enum/ecompany-size';
import { EIndustry } from '../../models/enum/eindustry';
import { EBusinessType } from '../../models/enum/ebusiness-type';
import { UserResponse } from '../../models/response/user-response';
import { UserService } from '../../services/user/user.service';
import { ContactResponse } from '../../models/response/contact-response';
import { ContactService } from '../../services/contact/contact.service';
import { CardModule } from 'primeng/card';
import { DrawerModule } from 'primeng/drawer';

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
    selector: 'app-company',
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
        CardModule,
        DrawerModule
    ],
    templateUrl: './company.component.html',
    providers: [ConfirmationService]
})
export class CompanyComponent implements OnInit {

    companyDialog: boolean = false;
    companies = signal<CompanyResponse[]>([]);

    company!: CompanyRequest;
    companyShow!: CompanyResponse;
    companyId!: string | null;

    visbility: boolean = false;

    selectedCompanies!: CompanyResponse[] | null;

    sizes = Object.values(ECompanySize);
    industries = Object.values(EIndustry);
    businessTypes = Object.values(EBusinessType);

    salesOwners!: UserResponse[];

    contacts!: ContactResponse[];

    submitted: boolean = false;

    @ViewChild('dt') dt!: Table;
    exportColumns!: ExportColumn[];

    cols!: Column[];

    constructor(
        private messageService: MessageService,
        private companyService: CompanyService,
        private userService: UserService,
        private contactService: ContactService,
        private confirmationService: ConfirmationService
    ) {

    }

    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit(): void {
        this.loadCompanies();
        this.loadSalesOwners();
        this.loadContacts();
        this.initColsAndExportCols();
    }

    loadCompanies() {
        this.companyService.getAllCompanies().subscribe({
            next: (response) => {
                this.companies.set(response);
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while loading companies' });
            }
        });
    }
    loadSalesOwners() {
        this.userService.getAllUsers().subscribe({
            next: (response) => {
                this.salesOwners = response;
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while loading sales owners' });
            }
        });
    }
    loadContacts(){
        this.contactService.getAllContacts().subscribe({
            next: (response) => {
                this.contacts = response;
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while loading contacts' });
            }
        });
    }

    initColsAndExportCols(): void {
        this.cols = [
            { field: 'id', header: 'ID', customExportHeader: 'ID' },
            { field: 'name', header: 'Name', customExportHeader: 'Name' },
            { field: 'imageUrl', header: 'Image URL', customExportHeader: 'Image URL' },
            { field: 'size', header: 'Size', customExportHeader: 'Size' },
            { field: 'industry', header: 'Industry', customExportHeader: 'Industry' },
            { field: 'businessType', header: 'Business Type', customExportHeader: 'Business Type' },
            { field: 'country', header: 'Country', customExportHeader: 'Country' },
            { field: 'city', header: 'City', customExportHeader: 'City' },
            { field: 'address', header: 'Address', customExportHeader: 'Address' },
            { field: 'website', header: 'Website', customExportHeader: 'Website' },
            { field: 'contacts', header: 'Contacts', customExportHeader: 'Contacts' },
            { field: 'salesOwner', header: 'Sales Owner', customExportHeader: 'Sales Owner' },
            { field: 'createdBy', header: 'Created by', customExportHeader: 'Created by' },
            { field: 'updatedBy', header: 'Updated by', customExportHeader: 'Updated by' },
            { field: 'createdAt', header: 'Created at', customExportHeader: 'Created at' },
            { field: 'updatedAt', header: 'Updated at', customExportHeader: 'Updated at' }
        ];

        this.exportColumns = this.cols.map((col) => ({ title: col.header, dataKey: col.field }));
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.company = {
            name: '',
            imageUrl: '',
            size: ECompanySize.SMALL,
            industry: EIndustry.OTHER,
            businessType: EBusinessType.B2B,
            country: '',
            city: '',
            address: '',
            website: '',
            contactsId: [],
            salesOwnerId: ''
        }
        this.submitted = false;
        this.companyDialog = true;
    }

    hideDialog() {
        this.companyDialog = false;
        this.submitted = false;
        this.companyId = null;
    }

    openVisibility(company: CompanyResponse){
        this.companyShow = company;
        this.visbility = true
    }
    editCompany(company: CompanyResponse) {
        this.company = {
            name: company.name,
            imageUrl: company.imageUrl,
            size: company.size,
            industry: company.industry,
            businessType: company.businessType,
            country: company.country,
            city: company.city,
            address: company.address,
            website: company.website,
            contactsId: company.contacts.map((contact) => contact.id),
            salesOwnerId: company.salesOwner.id
        }
        this.companyId = company.id;
        this.companyDialog = true;
    }

    deleteCompany(company: CompanyResponse) {
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + company.name + '? (Note: This will also delete all contacts of this company)',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.companyService.deleteCompany(company.id).subscribe({
                    next: () => {
                        this.loadCompanies();
                        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Company deleted', life: 3000 });
                    },
                    error: () => {
                        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while deleting company' });
                    }
                })
            }
        });
    }

    deleteSelectedCompanies() {
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete the selected companies?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.selectedCompanies?.forEach((company) => {
                    this.companyService.deleteCompany(company.id).subscribe({
                        next: () => {
                            this.loadCompanies();
                            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Companies deleted', life: 3000 });
                        },
                        error: () => {
                            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while deleting companies' });
                        }
                    });
                });
            }
        });
    }

    findIndexById(id: string): string {
        let index = -1;
        for (let i = 0; i < this.companies.length; i++) {
            if (this.companies()[i].id === id) {
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


    updateCompany() {
        this.submitted = true;
        if (this.companyId === null) {
            return;
        }
        this.companyService.updateCompany(this.companyId, this.company).subscribe({
            next: () => {
                this.loadCompanies();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Company updated', life: 3000 });
                this.companyDialog = false;
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while updating company' });
            }
        });
        this.companyId = null;
    }

    saveCompany() {
        this.submitted = true;
        this.companyService.createCompany(this.company).subscribe({
            next: () => {
                this.loadCompanies();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Company created', life: 3000 });
                this.companyDialog = false;
            },
            error: (error) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'An error occurred while creating company' });
            }
        });
    }
}

import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { QuoteService } from '../../services/quote/quote.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { SelectModule } from 'primeng/select';
import { Table, TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { ConfirmationService, MessageService } from 'primeng/api';
import { QuoteResponse } from '../../models/response/quote-response';
import { QuoteRequest } from '../../models/request/quote-request';
import { CompanyResponse } from '../../models/response/company-response';
import { ContactResponse } from '../../models/response/contact-response';
import { CompanyService } from '../../services/company/company.service';
import { ContactService } from '../../services/contact/contact.service';
import { ProductQuoteResponse } from '../../models/response/product-quote-response';
import { InventoryItemResponse } from '../../models/response/inventory-item-response';
import { InventoryItemService } from '../../services/item/inventory-item.service';


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
    selector: 'app-quote',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FormsModule,
        ButtonModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        TextareaModule,
        SelectModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        TagModule,
        InputIconModule,
        IconFieldModule,
        ConfirmDialogModule,
        MultiSelectModule,
        TooltipModule
    ],
    templateUrl: './quote.component.html',
    providers: [ConfirmationService]
})
export class QuoteComponent implements OnInit {

    quoteDialog: boolean = false;
    quotes = signal<QuoteResponse[]>([]);
    
    companies!: CompanyResponse[];
    contacts!: ContactResponse[];
    items!: InventoryItemResponse[];

    quote!: QuoteRequest;
    quoteId: string | null = null;

    selectedQuotes!: QuoteResponse[];

    submitted: boolean = false;
    
    @ViewChild('dt') dt!: Table;
    exportColumns!: ExportColumn[];
    
    cols!: Column[];
        
    constructor(
        private quoteService: QuoteService,
        private companyService: CompanyService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private itemService: InventoryItemService
    ) { }

    ngOnInit(): void {
        this.loadQuotes(); 
        this.loadCompanies();
        this.initColsAndExportCols();
        this.loadItems();
    }

    loadQuotes(){
        this.quoteService.getQuotes().subscribe({
            next: (quotes) => {
                this.quotes.set(quotes);
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Error loading quotes'});
            }
        });
    }

    loadCompanies() {
        this.companyService.getAllCompanies().subscribe({
            next: (companies) => {
                this.companies = companies;
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to load companies'});
            }
        });
    }

    loadItems(){
        this.itemService.getAllItems().subscribe({
            next: (items) => {
                this.items = items;
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to load items'});
            }
        });
    }

    initColsAndExportCols(): void {
        this.cols = [
            { field: 'quoteNumber', header: 'Quote Number' },
        ];

        this.exportColumns = this.cols.map(col => ({ title: col.header, dataKey: col.field }));
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    exportCSV() {
        this.dt.exportCSV();
    }

    openNew() {
        this.quote = {
            quoteName: '',
            companyId: '',
            contactId: '',
            description: '',
            productQuoteRequest: []
        };
        this.submitted = false;
        this.quoteDialog = true;
    }

    hideDialog() {
        this.quoteDialog = false;
        this.submitted = false;
        this.quoteId = null;
    }

    editQuote(quote: QuoteResponse) {
        this.quote = {
            quoteName: quote.quoteName,
            companyId: quote.company.id,
            contactId: quote.contact.id,
            description: quote.description,
            productQuoteRequest: quote.productQuote.map(item => ({
                itemId: item.item.id,
                quantity: item.quantity,
                margeDeGain: item.margeDeGain,
                discount: item.discount,
            }))
        };
        this.onCompanySelect({ value: quote.company.id });
        this.quoteId = quote.id;
        this.quoteDialog = true;
    }

    onCompanySelect(event: any) {
        this.companyService.getCompany(event.value).subscribe({
            next: (company) => {
                this.contacts = company.contacts;
            },
            error: () => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Error loading contacts'});
            }
        });
    }

    deleteQuote(quote: QuoteResponse){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + quote.quoteName + '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.quoteService.deleteQuote(quote.id).subscribe({
                    next: () => {
                        this.loadQuotes();
                        this.messageService.add({severity:'success', summary:'Successful', detail:'Quote Deleted', life: 3000});
                    },
                    error: () => {
                        this.messageService.add({severity:'error', summary:'Error', detail:'Error deleting quote'});
                    }
                });
            }
        });
    }
    deleteSelectedQuotes() {
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete the selected quotes?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.selectedQuotes.forEach((quote) => {
                    this.quoteService.deleteQuote(quote.id).subscribe({
                        next: () => {
                            this.loadQuotes();
                            this.messageService.add({severity:'success', summary:'Successful', detail:'Quotes Deleted', life: 3000});
                        },
                        error: () => {
                            this.messageService.add({severity:'error', summary:'Error', detail:'Error deleting quotes'});
                        }
                    });
                });
            }
        });
    }

    findIndexById(id: string): string {
        let index = -1;
        for (let i = 0; i < this.quotes.length; i++) {
            if (this.quotes()[i].id === id) {
                index = i;
                break;
            }
        }
        return index + '';
    }

    updateQuote(): void {
        this.submitted = true;
        if(this.quoteId === null){
            return;
        }
        this.quoteService.updateQuote(this.quoteId, this.quote).subscribe({
            next: () => {
                this.loadQuotes();
                this.messageService.add({severity:'success', summary:'Successful', detail:'Quote Updated', life: 3000});
                this.quoteDialog = false;
            },
            error: () => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Error updating quote'});
            }
        });
        this.quoteId = null;
    }

    saveQuote(): void {
        this.submitted = true;
        this.quoteService.createQuote(this.quote).subscribe({
            next: () => {
                this.loadQuotes();
                this.messageService.add({severity:'success', summary:'Successful', detail:'Quote Created', life: 3000});
                this.quoteDialog = false;
            },
            error: () => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Error creating quote'});
            }
        });
    }
    margeDeGain(productQuotes: ProductQuoteResponse[]): number {
        return productQuotes.reduce((total, product) => {
            return total + product.item.costPrice * product.margeDeGain * (1 - product.discount) * product.quantity;
        }, 0);
    }

    prixDeVentes(productQuotes: ProductQuoteResponse[]): number {
        return productQuotes.reduce((total, product) => {
            const prixHT = product.item.costPrice * (1 + product.margeDeGain) * (1 - product.discount);
            const prixTTC = prixHT * (1 + product.item.tva);
            return total + prixTTC * product.quantity;
        }, 0);
    }

    addProductQuote() {
        this.quote.productQuoteRequest.push({
            itemId: '',
            quantity: 0,
            margeDeGain: 0,
            discount: 0
        });
    }
    removeProductQuote(index: number) {
        this.quote.productQuoteRequest.splice(index, 1);
    }

    getQuantity(itemId: string): number {
        return this.items.find(item => item.id === itemId)?.quantity || 0;
    }
}

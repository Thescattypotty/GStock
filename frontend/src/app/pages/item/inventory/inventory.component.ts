import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { MultiSelectModule } from 'primeng/multiselect';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { TooltipModule } from 'primeng/tooltip';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InventoryItemResponse } from '../../../models/response/inventory-item-response';
import { InventoryItemRequest } from '../../../models/request/inventory-item-request';
import { InventoryItemService } from '../../../services/item/inventory-item.service';
import { ItemCategoryResponse } from '../../../models/response/item-category-response';
import { ItemCategoryService } from '../../../services/item/item-category.service';

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
    selector: 'app-inventory',
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
    templateUrl: './inventory.component.html',
    providers: [ConfirmationService]
})
export class InventoryComponent implements OnInit{

    itemDialog: boolean = false;
    items = signal<InventoryItemResponse[]>([]);

    categories: ItemCategoryResponse[] = [];

    item!: InventoryItemRequest;
    itemId!: string | null;

    selectedItems: InventoryItemResponse[] = [];
    submitted: boolean = false;

    @ViewChild('dt') dt!: Table;
    exportColumns!: ExportColumn[];
    
    cols!: Column[];

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private itemService: InventoryItemService,
        private categoryService: ItemCategoryService
    ){

    }

    ngOnInit(): void {
        this.loadItems();
        this.loadCategories();
        this.initColsAndExportCols();
    }
    loadCategories(): void {
        this.categoryService.getAllCategories().subscribe({
            next: (categories) => {
                this.categories = categories;
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to load categories'});
            }
        });
    }
    loadItems(): void {
        this.itemService.getAllItems().subscribe({
            next: (items) => {
                this.items.set(items);
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to load items'});
            }
        })
    }

    initColsAndExportCols(): void {
        this.cols = [
            { field: 'id', header: 'ID', customExportHeader: 'ID' },
            { field: 'name', header: 'Name', customExportHeader: 'Name' },
            { field: 'description', header: 'Description', customExportHeader: 'Description' },
            { field: 'costPrice', header: 'costPrice', customExportHeader: 'CostPrice' },
            { field: 'quantity', header: 'Quantity', customExportHeader: 'Quantity' },
            { field: 'tva', header: 'TVA', customExportHeader: 'TVA' },
            { field: 'category', header: 'Category', customExportHeader: 'Category' },
            { field: 'createdAt', header: 'Created At', customExportHeader: 'Created At' },
            { field: 'updatedAt', header: 'Updated At', customExportHeader: 'Updated At' },
            { field: 'createdBy', header: 'Created By', customExportHeader: 'Created By' },
            { field: 'updatedBy', header: 'Updated By', customExportHeader: 'Updated By' }
        ];
        this.exportColumns = this.cols.map((col) => ({ title: col.header, dataKey: col.field }));
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    exportCSV() {
        this.dt.exportCSV();
    }

    openNew(){
        this.item = {
            name: '',
            description: '',
            quantity: 0,
            costPrice: 0.0,
            tva: 0.0,
            categoryId: ''
        };
        this.submitted = false;
        this.itemDialog = true;
    }
    hideDialog(){
        this.itemDialog = false;
        this.submitted = false;
    }
    editItem(item: InventoryItemResponse){
        this.item = {
            name: item.name,
            description: item.description,
            quantity: item.quantity,
            costPrice: item.costPrice,
            tva: item.tva,
            categoryId: item.category.id
        };
        this.itemId = item.id;
        this.itemDialog = true;
    }

    deleteItem(item: InventoryItemResponse){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + item.name + '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.itemService.deleteItem(item.id).subscribe({
                    next: () => {
                        this.items.set(this.items().filter(val => val.id !== item.id));
                        this.messageService.add({severity:'success', summary:'Success', detail:'Item deleted', life: 3000});
                    },
                    error: (error) => {
                        this.messageService.add({severity:'error', summary:'Error', detail:'Failed to delete item', life: 3000});
                    }
                });
            }
        });
    }

    deleteSelectedItems(){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete the selected items?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.selectedItems.forEach((item) => {
                    this.itemService.deleteItem(item.id).subscribe({
                        next: () => {
                            this.items.set(this.items().filter(val => val.id !== item.id));
                            this.messageService.add({severity:'success', summary:'Success', detail:'Item deleted', life: 3000});
                        },
                        error: (error) => {
                            this.messageService.add({severity:'error', summary:'Error', detail:'Failed to delete item', life: 3000});
                        }
                    });
                });
            }
        });
    }
    findIndexById(id: string): string {
        let index = -1;
        for (let i = 0; i < this.items.length; i++) {
            if (this.items()[i].id === id) {
                index = i;
                break;
            }
        }
        return index + '';
    }

    updateItem(): void {
        this.submitted = true;
        if(this.itemId === null){
            return;
        }
        this.itemService.updateItem(this.itemId, this.item).subscribe({
            next: () => {
                this.loadItems();
                this.messageService.add({severity:'success', summary:'Success', detail:'Item updated', life: 3000});
                this.itemDialog = false
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to update item', life: 3000});
            }
        });
    }

    saveItem(): void {
        this.submitted = true;
        this.itemService.createItem(this.item).subscribe({
            next: () => {
                this.loadItems();
                this.messageService.add({severity:'success', summary:'Success', detail:'Item created', life: 3000});
                this.itemDialog = false;
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to create item', life: 3000});
            }
        });
    }


}

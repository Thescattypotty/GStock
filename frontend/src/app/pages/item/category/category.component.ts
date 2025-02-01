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
import { ItemCategoryResponse } from '../../../models/response/item-category-response';
import { ItemCategoryRequest } from '../../../models/request/item-category-request';
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
    selector: 'app-category',
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
    templateUrl: './category.component.html',
    providers: [ConfirmationService]
})

export class CategoryComponent implements OnInit{

    categoryDialog: boolean = false;
    categories = signal<ItemCategoryResponse[]>([]);

    category!: ItemCategoryRequest;
    categoryId!: string | null;

    selectedCategories!: ItemCategoryResponse[] | null;
    submitted: boolean = false;
    
    @ViewChild('dt') dt!: Table;
    exportColumns!: ExportColumn[];

    cols!: Column[];

    constructor(
        private messageService: MessageService,
        private categoryService: ItemCategoryService,
        private confirmationService: ConfirmationService
    ){

    }
    
    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit(): void {
        this.loadCategories();
        this.initColsAndExportCols();
    }

    loadCategories(): void {
        this.categoryService.getAllCategories().subscribe({
            next: (categories) => {
                this.categories.set(categories);
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to load categories'});
            }
        });
    }
    initColsAndExportCols(): void {
        this.cols = [
            { field: 'id', header: 'ID' , customExportHeader: 'ID'},
            { field: 'name', header: 'Name' , customExportHeader: 'Name'},
            { field: 'createdAt', header: 'Created At' ,  customExportHeader: 'Created At'},
            { field: 'updatedAt', header: 'Updated At' , customExportHeader: 'Updated At'},
            { field: 'createdBy', header: 'Created By' , customExportHeader: 'Created By'},
            { field: 'updatedBy', header: 'Updated By' , customExportHeader: 'Updated By'}
        ];
        this.exportColumns = this.cols.map((col) => ({ title: col.header, dataKey: col.field }));
    }
    
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew(){
        this.category = {
            name: ''
        };
        this.submitted = false;
        this.categoryDialog = true;
    }
     
    hideDialog(){
        this.categoryDialog = false;
        this.submitted = false;
    }
    editCategory(category: ItemCategoryResponse){
        this.category = {
            name: category.name
        };
        this.categoryId = category.id;
        this.categoryDialog = true;
    }

    deleteCategory(category: ItemCategoryResponse){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + category.name + '? (Note that deleting the categorie also delete Items Associed)',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.categoryService.deleteCategory(category.id).subscribe({
                    next: () => {
                        this.loadCategories();
                        this.messageService.add({severity:'success', summary:'Success', detail:'Category deleted'});
                    },
                    error: (error) => {
                        this.messageService.add({severity:'error', summary:'Error', detail:'Failed to delete category'});
                    }
                });
            }
        });
    }

    deleteSelectedCategories(){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete the selected categories? (Note that deleting the categorie also delete Items Associed)',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.selectedCategories?.forEach((category) => {
                    this.categoryService.deleteCategory(category.id).subscribe({
                        next: () => {
                            this.loadCategories();
                            this.messageService.add({severity:'success', summary:'Success', detail:'Category deleted'});
                        },
                        error: (error) => {
                            this.messageService.add({severity:'error', summary:'Error', detail:'Failed to delete category'});
                        }
                    });
                });
                this.selectedCategories = null;
                this.messageService.add({severity:'success', summary:'Success', detail:'Categories deleted'});
            }
        });
    }

    findIndexById(id: string): string {
        let index = -1;
        for (let i = 0; i < this.categories.length; i++) {
            if (this.categories()[i].id === id) {
                index = i;
                break;
            }
        }
        return index + '';
    }

    updateCategory(){
        this.submitted = true;
        if(this.categoryId === null){
            return;
        }
        this.categoryService.updateCategory(this.categoryId, this.category).subscribe({
            next: () => {
                this.loadCategories();
                this.messageService.add({severity:'success', summary:'Success', detail:'Category updated'});
                this.categoryDialog = false;
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to update category'});
            }
        });
    }

    saveCategory(): void {
        this.submitted = true;
        this.categoryService.createCategory(this.category).subscribe({
            next: () => {
                this.loadCategories();
                this.messageService.add({severity:'success', summary:'Success', detail:'Category created'});
                this.categoryDialog = false;
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to create category'});
            }
        });
    }

}

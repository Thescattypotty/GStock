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
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { UserResponse } from '../../models/user-response';
import { ERole } from '../../models/erole';
import { UserService } from '../../services/user/user.service';
import { UserRequest } from '../../models/user-request';
import { RegisterRequest } from '../../models/register-request';


interface Column {
    field: string;
    header: string;
    customExportHeader?: string;
}

interface ExportColumn {
    title: string;
    dataKey: string;
}

interface Role {
    name: string;
    value: ERole;
}

@Component({
    selector: 'app-user',
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
        MultiSelectModule

    ],
    templateUrl: './user.component.html',
    providers: [ConfirmationService]
})
export class UserComponent implements OnInit{

    userDialog: boolean = false;
    userUpdateDialog: boolean = false;
    users = signal<UserResponse[]>([]);
    user!: RegisterRequest;
    userUpdate!: UserRequest;
    userUpdatedId!: string | null;
    selectedUsers!: UserResponse[] | null;
    submitted: boolean = false;
    roles!: ERole[];

    @ViewChild('dt') dt!: Table;
    exportColumns!: ExportColumn[];

    cols!: Column[];

    constructor(
        private userService: UserService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService
    ) {
        
    }
    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit(): void {
        this.loadUsers();
        this.initColsAndExportCols();
        this.initRoles();
    }

    loadUsers() {
        this.userService.getAllUsers().subscribe({
            next: (users) => {
                this.users.set(users);
            },
            error: (error) => {
                this.messageService.add({severity: 'error', summary: 'Error', detail: 'Error loading users'});
            }
        });
    }

    initRoles() {
        this.roles = Object.values(ERole);
        console.log(this.roles);
    }

    initColsAndExportCols() {
        this.cols = [
            { field: 'id', header: 'ID', customExportHeader: 'ID' },
            { field: 'firstName', header: 'First Name', customExportHeader: 'First Name' },
            { field: 'lastName', header: 'Last Name', customExportHeader: 'Last Name' },
            { field: 'username', header: 'Username', customExportHeader: 'Username' },
            { field: 'email', header: 'Email', customExportHeader: 'Email' },
            { field: 'address', header: 'Address', customExportHeader: 'Address' },
            { field: 'phone', header: 'Phone', customExportHeader: 'Phone' },
            { field: 'imageUrl', header: 'Image URL', customExportHeader: 'Image URL' },
            { field: 'roles', header: 'Roles', customExportHeader: 'Roles' },
            { field: 'createdAt', header: 'Created At', customExportHeader: 'Created At' },
            { field: 'updatedAt', header: 'Updated At', customExportHeader: 'Updated At' }
            
        ];
        this.exportColumns = this.cols.map((col) => ({ title: col.header, dataKey: col.field}));
    }
    onGlobalFilter(table: Table, event: Event){
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }
    openNew() {
        this.user = {
            username: '',
            email: '',
            password: '',
            firstName: '',
            lastName: '',
            address: '',
            phone: '',
            imageUrl: '',
            roles: []
        };
        this.submitted = false;
        this.userDialog = true;
    }
    openUpdate(){
        this.userUpdate = {
            firstName: '',
            lastName: '',
            address: '',
            phone: '',
            imageUrl: '',
            roles: []
        };
        this.submitted = false;
        this.userUpdateDialog = true;
    }


    editUser(UserResponse: UserResponse){
        this.user = { ...UserResponse, password: '' };
        this.userService.getUser(UserResponse.id).subscribe({
            next: (user) => {
                this.userUpdate = {
                    firstName: user.firstName,
                    lastName: user.lastName,
                    address: user.address,
                    phone: user.phone,
                    imageUrl: user.imageUrl,
                    roles: user.roles
                }
                this.userUpdatedId = user.id;
                this.userUpdateDialog = true;
            },
            error: (error) => {
                this.messageService.add({severity: 'error', summary: 'Error', detail: 'Error loading user'});
            }
        });
    }

    deleteSelectedUsers(){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete the selected users?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.selectedUsers?.forEach((user) => {
                    this.userService.deleteUser(user.id).subscribe({
                        next: () => {
                            this.loadUsers();
                        },
                        error: (error) => {
                            this.messageService.add({severity: 'error', summary: 'Error', detail: 'Error deleting users'});
                        }
                    });
                });
                this.selectedUsers = null;
                this.messageService.add({severity: 'success', summary: 'Successful', detail: 'Users Deleted', life: 3000});
            }
        });
    }
    hideDialog(){
        this.userDialog = false;
        this.submitted = false;
    }
    hideUpdateDialog(){
        this.userUpdateDialog = false;
        this.submitted = false;
    }
    deleteUser(userResponse: UserResponse){
        this.confirmationService.confirm({
            message: 'Are you sure you want to delete ' + userResponse.username + '?',
            header: 'Confirm',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.userService.deleteUser(userResponse.id).subscribe({
                    next: () => {
                        this.loadUsers();
                        this.messageService.add({severity: 'success', summary: 'Successful', detail: 'User Deleted', life: 3000});
                    },
                    error: (error) => {
                        this.messageService.add({severity: 'error', summary: 'Error', detail: 'Error deleting user'});
                    }
                })
            }
        });
    }

    findIndexById(id: string): string{
        let index = -1;
        for (let i = 0; i < this.users().length; i++){
            if (this.users()[i].id === id){
                index = i;
                break;
            }
        }
        return index + '';
    }

    saveUser(){
        this.submitted = true;
        this.userService.createUser(this.user).subscribe({
            next: () => {
                this.userDialog = false;
                this.loadUsers();
                this.messageService.add({severity: 'success', summary: 'Successful', detail: 'User Created', life: 3000});
            },
            error: (error) => {
                this.messageService.add({severity: 'error', summary: 'Error', detail: 'Error creating user'});
            }
        });
    }
    updateUser(){
        this.submitted = true;
        if(this.userUpdatedId === null){
            return;
        }
        this.userService.updateUser(this.userUpdatedId, this.userUpdate).subscribe({
            next: () => {
                this.userUpdateDialog = false;
                this.loadUsers();
                this.messageService.add({severity: 'success', summary: 'Successful', detail: 'User Updated', life: 3000});
            },
            error: (error) => {
                this.messageService.add({severity: 'error', summary: 'Error', detail: 'Error updating user'});
            }
        });
        this.userUpdatedId = null;
    }

}

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { UserResponse } from '../../models/response/user-response';
import { Router } from '@angular/router';
import { UserRequest } from '../../models/request/user-request';
import { ChangePasswordRequest } from '../../models/request/change-password-request';
import { ChangeUsernameRequest } from '../../models/request/change-username-request';
import { UserService } from '../../services/user/user.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { MultiSelectModule } from 'primeng/multiselect';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { ERole } from '../../models/enum/erole';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { StyleClassModule } from 'primeng/styleclass';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { TextareaModule } from 'primeng/textarea';

@Component({
    selector: 'app-profile',
    imports: [
        CommonModule,
        FormsModule,
        DialogModule,
        MultiSelectModule,
        ButtonModule,
        ConfirmDialogModule,
        CardModule,
        StyleClassModule,
        InputTextModule,
        PasswordModule,
        TextareaModule,
    ],
    templateUrl: './profile.component.html',
    providers: [ConfirmationService]
})
export class ProfileComponent implements OnInit{

    public SUPER_ADMINISTRATEUR: ERole = ERole.SUPER_ADMINISTRATEUR;
    
    submitted: boolean = false;

    user!: UserResponse;
    roles!: ERole[];

    userDialog: boolean = false;
    userUpdate!: UserRequest;
    
    changePasswordRequest!: ChangePasswordRequest;
    changePasswordDialog: boolean = false;

    changeUsernameRequest!: ChangeUsernameRequest;
    changeUsernameDialog: boolean = false;

    constructor(
        private authService: AuthService,
        private userService: UserService,
        private messageService: MessageService,
        private router: Router
    ) {

    }

    ngOnInit(): void {
        this.loadUserData();
        this.initRoles();
    }

    loadUserData(): void{
        this.authService.currentUser().subscribe({
            next: (response) => {
                this.user = response;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }
    initRoles() {
        this.roles = Object.values(ERole);
        console.log(this.roles);
    }


    openUpdate() {
        this.userUpdate = {
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

    openChangePassword() {
        this.changePasswordRequest = {
            oldPassword: '',
            newPassword: ''
        }
        this.submitted = false;
        this.changePasswordDialog = true;
    }
    openChangeUsername() {
        this.changeUsernameRequest = {
            newUsername: ''
        }
        this.submitted = false;
        this.changeUsernameDialog = true;
    }

    editUser(){
        this.userUpdate = {
            firstName: this.user.firstName,
            lastName: this.user.lastName,
            address: this.user.address,
            phone: this.user.phone,
            imageUrl: this.user.imageUrl,
            roles: this.user.roles
        }
        this.userDialog = true;
    }

    changePassword(){
        this.changePasswordDialog = true;
    }

    changeUsername(){
        this.changeUsernameDialog = true;
    }

    hideDialog() {
        this.userDialog = false;
        this.submitted = false;
    }

    hideChangePasswordDialog() {
        this.changePasswordDialog = false;
        this.submitted = false;
    }

    hideChangeUsernameDialog() {
        this.changeUsernameDialog = false;
        this.submitted = false;
    }

    saveChangePassword(){
        this.submitted = true;

        this.userService.changePassword(this.user.id, this.changePasswordRequest).subscribe({
            next: () => {
                this.changePasswordDialog = false;
                this.loadUserData();
                this.messageService.add({severity:'success', summary:'Success', detail:'Password changed', life: 3000});
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to change password', life: 3000});
            }
        });
    }

    saveChangeUsername(){
        this.submitted = true;

        this.userService.changeUsername(this.user.id, this.changeUsernameRequest).subscribe({
            next: () => {
                this.changeUsernameDialog = false;
                this.loadUserData();
                this.messageService.add({severity:'success', summary:'Success', detail:'Username changed', life: 3000});
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to change username', life: 3000});
            }
        });
    }

    updateUser(){
        this.submitted = true;

        this.userService.updateUser(this.user.id, this.userUpdate).subscribe({
            next: () => {
                this.userDialog = false;
                this.loadUserData();
                this.messageService.add({severity:'success', summary:'Success', detail:'User updated', life: 3000});
            },
            error: (error) => {
                this.messageService.add({severity:'error', summary:'Error', detail:'Failed to update user', life: 3000});
            }
        });
    }
}

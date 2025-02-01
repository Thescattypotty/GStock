import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../../layout/component/app.floatingconfigurator';
import { AuthService } from '../../../services/auth/auth.service';
import { LoginRequest } from '../../../models/login-request';
import { JwtResponse } from '../../../models/jwt-response';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [
        AppFloatingConfigurator,
        ButtonModule,
        CheckboxModule,
        InputTextModule,
        PasswordModule,
        FormsModule,
        RouterModule,
        RippleModule
    ],
    templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit{

    loginRequest: LoginRequest = { username: '', password: '' };
    jwtResponse: JwtResponse | null = null;
    
    constructor(
        private authService: AuthService,
        private router: Router,
        private messageService: MessageService
    ){

    }
    ngOnInit(): void {
        if (this.authService.isLoggedIn()){
            this.messageService.add({severity:'info', summary:'Info', detail:'Already logged in'});
            this.router.navigate(['/dashboard']);
        }
    }

    login(): void{
        if (this.isLoginRequestValid()){
            this.authService.login(this.loginRequest).subscribe({
                next: (jwtResponse: JwtResponse) => {
                    this.jwtResponse = jwtResponse;
                    this.authService.setToken(this.jwtResponse.accessToken.valueOf())
                    this.messageService.add({severity:'success', summary:'Success', detail:'Login successful'});
                    this.router.navigate(['/dashboard']);
                },
                error: (error) => {
                    this.messageService.add({severity:'error', summary:'Error', detail:'Login failed'});
                    this.router.navigate(['500']);
                }
            })
        }else{
            this.messageService.add({severity:'error', summary:'Error', detail:'Invalid login request'});
        }
        
    }

    isLoginRequestValid(): boolean{
        return this.loginRequest.username.length > 0 && this.loginRequest.password.length > 0;
    }
    checked: boolean = false;
}

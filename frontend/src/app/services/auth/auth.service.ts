import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../models/request/login-request';
import { BehaviorSubject, firstValueFrom, Observable } from 'rxjs';
import { JwtResponse } from '../../models/response/jwt-response';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private API_URL = 'http://localhost:8080/api/v1/auth';
    private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
    public isAuthenticated = this.isAuthenticatedSubject.asObservable();
    
    constructor(
        private http: HttpClient
    ) {
        this.checkToken();
    }

    login(loginRequest: LoginRequest): Observable<JwtResponse> {
        return this.http.post<JwtResponse>(`${this.API_URL}/login`, loginRequest);
    }

    setToken(accessToken: string): void {
        localStorage.setItem('accessToken', accessToken);
        this.isAuthenticatedSubject.next(true);
    }

    isLoggedIn(): boolean {
        return this.isLocalStorageAvailable() && !!localStorage.getItem('accessToken');
    }

    logout(): Promise<void> {
        return firstValueFrom(this.http.post(`${this.API_URL}/logout`, null))
            .then(() => {
                localStorage.removeItem('accessToken');
                this.isAuthenticatedSubject.next(false);
            })
            .catch((error) => {
                localStorage.removeItem('accessToken');
                this.isAuthenticatedSubject.next(false);
                console.log(error);
            }
        );
    }

    private checkToken(): void {
        if (this.isLocalStorageAvailable()) {
            const token = localStorage.getItem('accessToken');
            this.isAuthenticatedSubject.next(!!token);
            return;
        }
        this.isAuthenticatedSubject.next(false);
    }
    private isLocalStorageAvailable(): boolean {
        try {
            return typeof localStorage !== 'undefined';
        } catch (e) {
            return false;
        }
    }

}

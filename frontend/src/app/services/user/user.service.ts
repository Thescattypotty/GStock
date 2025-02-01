import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterRequest } from '../../models/request/register-request';
import { UserRequest } from '../../models/request/user-request';
import { ChangePasswordRequest } from '../../models/request/change-password-request';
import { ChangeUsernameRequest } from '../../models/request/change-username-request';
import { UserResponse } from '../../models/response/user-response';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private API_URL = 'http://localhost:8080/api/v1/users';
    constructor(
        private http: HttpClient
    ) {

    }
    createUser(registerRequest: RegisterRequest): Observable<void> {
        return this.http.post<void>(`${this.API_URL}`, registerRequest);
    }

    updateUser(id: string, userRequest: UserRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}`, userRequest);
    }

    changePassword(id: string, changePasswordRequest: ChangePasswordRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}/change-password`, changePasswordRequest);
    }

    changeUsername(id: string, changeUsernameRequest: ChangeUsernameRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}/change-username`, changeUsernameRequest);
    }

    deleteUser(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${id}`);
    }
    
    getUser(id: string): Observable<UserResponse> {
        return this.http.get<UserResponse>(`${this.API_URL}/${id}`);
    }

    getAllUsers(): Observable<UserResponse[]> {
        return this.http.get<UserResponse[]>(`${this.API_URL}`);
    }

}

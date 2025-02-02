import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ContactRequest } from '../../models/request/contact-request';
import { Observable } from 'rxjs';
import { ContactResponse } from '../../models/response/contact-response';

@Injectable({
    providedIn: 'root'
})
export class ContactService {

    private API_URL = 'http://localhost:8080/api/v1/contacts';
    
    constructor(
        private http: HttpClient
    ) {

    }
    createContact(contactRequest: ContactRequest): Observable<void> {
        return this.http.post<void>(`${this.API_URL}`, contactRequest);
    }

    updateContact(id: string, contactRequest: ContactRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}`, contactRequest);
    }
    deleteContact(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${id}`);
    }
    getContact(id: string): Observable<ContactResponse> {
        return this.http.get<ContactResponse>(`${this.API_URL}/${id}`);
    }
    getAllContacts(): Observable<ContactResponse[]> {
        return this.http.get<ContactResponse[]>(`${this.API_URL}`);
    }

}

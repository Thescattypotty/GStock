import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CompanyRequest } from '../../models/request/company-request';
import { Observable } from 'rxjs';
import { CompanyResponse } from '../../models/response/company-response';

@Injectable({
    providedIn: 'root'
})
export class CompanyService {

    private API_URL = 'http://localhost:8080/api/v1/companies';

    constructor(
        private http: HttpClient
    ) {

    }

    createCompany(companyRequest: CompanyRequest): Observable<void> {
        return this.http.post<void>(`${this.API_URL}`, companyRequest);
    }
    
    updateCompany(id: string, companyRequest: CompanyRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}`, companyRequest);
    }

    deleteCompany(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${id}`);
    }

    getCompany(id: string): Observable<CompanyResponse> {
        return this.http.get<CompanyResponse>(`${this.API_URL}/${id}`);
    }

    getAllCompanies(): Observable<CompanyResponse[]> {
        return this.http.get<CompanyResponse[]>(`${this.API_URL}`);
    }

}

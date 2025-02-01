import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ItemCategoryRequest } from '../../models/request/item-category-request';
import { Observable } from 'rxjs';
import { ItemCategoryResponse } from '../../models/response/item-category-response';

@Injectable({
    providedIn: 'root'
})
export class ItemCategoryService {

    private API_URL = 'http://localhost:8080/api/v1/item-categories';
    constructor(
        private http: HttpClient
    ) { 

    }
    createCategory(categoryRequest: ItemCategoryRequest): Observable<void> {
        return this.http.post<void>(`${this.API_URL}`, categoryRequest);
    }

    updateCategory(id: string, categoryRequest: ItemCategoryRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}`, categoryRequest);
    }
    
    deleteCategory(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${id}`);
    }
    
    getCategory(id: string): Observable<ItemCategoryResponse> {
        return this.http.get<ItemCategoryResponse>(`${this.API_URL}/${id}`);
    }
    
    getAllCategories(): Observable<ItemCategoryResponse[]> {
        return this.http.get<ItemCategoryResponse[]>(`${this.API_URL}`);
    }


}

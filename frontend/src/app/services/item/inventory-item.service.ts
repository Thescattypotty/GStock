import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { InventoryItemRequest } from '../../models/request/inventory-item-request';
import { Observable } from 'rxjs';
import { InventoryItemResponse } from '../../models/response/inventory-item-response';

@Injectable({
    providedIn: 'root'
})
export class InventoryItemService {
    private API_URL = 'http://localhost:8080/api/v1/inventory-items';
    constructor(
        private http: HttpClient
    ) {

    }

    createItem(itemRequest: InventoryItemRequest): Observable<void> {
        return this.http.post<void>(`${this.API_URL}`, itemRequest);
    }
    
    updateItem(id: string, itemRequest: InventoryItemRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}`, itemRequest);
    }

    deleteItem(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${id}`);
    }

    getItem(id: string): Observable<InventoryItemResponse> {
        return this.http.get<InventoryItemResponse>(`${this.API_URL}/${id}`);
    }

    getAllItems(): Observable<InventoryItemResponse[]> {
        return this.http.get<InventoryItemResponse[]>(`${this.API_URL}`);
    }
}

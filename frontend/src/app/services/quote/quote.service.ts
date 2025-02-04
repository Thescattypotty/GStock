import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { QuoteRequest } from '../../models/request/quote-request';
import { Observable } from 'rxjs';
import { ProductQuoteRequest } from '../../models/request/product-quote-request';
import { QuoteResponse } from '../../models/response/quote-response';

@Injectable({
    providedIn: 'root'
})
export class QuoteService {

    private API_URL = 'http://localhost:8080/api/v1/quotes';
    constructor(
        private http: HttpClient
    ) {

    }

    createQuote(quoteRequest: QuoteRequest): Observable<void> {
        return this.http.post<void>(this.API_URL, quoteRequest);
    }

    updateQuote(id: string, quoteRequest: QuoteRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/${id}`, quoteRequest);
    }

    deleteQuote(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/${id}`);
    }

    updateProductQuote(id: string, productQuoteRequest: ProductQuoteRequest): Observable<void> {
        return this.http.put<void>(`${this.API_URL}/update-product/${id}`, productQuoteRequest);
    }

    deleteProductQuote(id: string): Observable<void> {
        return this.http.delete<void>(`${this.API_URL}/delete-product/${id}`);
    }

    getQuote(id: string): Observable<QuoteResponse> {
        return this.http.get<QuoteResponse>(`${this.API_URL}/${id}`);
    }
    getQuotes(): Observable<QuoteResponse[]> {
        return this.http.get<QuoteResponse[]>(this.API_URL);
    }
}

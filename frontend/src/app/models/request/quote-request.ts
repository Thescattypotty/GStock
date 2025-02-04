import { ProductQuoteRequest } from "./product-quote-request";

export interface QuoteRequest {
    quoteName: string;
    companyId: string;
    contactId: string;
    description: string;
    productQuoteRequest: ProductQuoteRequest[];
}

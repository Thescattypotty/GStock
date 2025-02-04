import { CompanyResponse } from "./company-response";
import { ContactResponse } from "./contact-response";
import { ProductQuoteResponse } from "./product-quote-response";

export interface QuoteResponse {
    id: string;
    quoteName: string;
    description: string;
    company: CompanyResponse;
    contact: ContactResponse;
    productQuote: ProductQuoteResponse[];
    createdAt: Date;
    updatedAt: Date;
    createdBy: string;
    updatedBy: string;
}

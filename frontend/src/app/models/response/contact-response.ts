import { CompanyResponse } from "./company-response";

export interface ContactResponse {
    id: string;
    fullName: string;
    imageUrl: string;
    email: string;
    title: string;
    phone: string;
    isCustomer: boolean;
    companyResponse: CompanyResponse;
    createdBy: string;
    updatedBy: string;
    createdAt: string;
    updatedAt: string;
}

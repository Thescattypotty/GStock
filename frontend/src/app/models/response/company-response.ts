import { EBusinessType } from "../enum/ebusiness-type";
import { ECompanySize } from "../enum/ecompany-size";
import { EIndustry } from "../enum/eindustry";
import { ContactResponse } from "./contact-response";
import { UserResponse } from "./user-response";

export interface CompanyResponse {
    id: string;
    name: string;
    imageUrl: string;
    size: ECompanySize;
    industry: EIndustry;
    businessType: EBusinessType;
    country: string;
    city: string;
    address: string;
    website: string;
    contacts: ContactResponse[];
    salesOwner: UserResponse;
    createdAt: Date;
    updatedAt: Date;
    createdBy: string;
    updatedBy: string;
}
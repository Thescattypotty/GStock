import { EBusinessType } from "../enum/ebusiness-type";
import { ECompanySize } from "../enum/ecompany-size";
import { EIndustry } from "../enum/eindustry";

export interface CompanyRequest {
    name: string;
    imageUrl?: string;
    size: ECompanySize;
    industry: EIndustry;
    businessType: EBusinessType;
    country: string;
    city: string;
    address: string;
    website?: string;
    contactsId: string[];
    salesOwnerId: string;
}
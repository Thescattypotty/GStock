import { ERole } from "../enum/erole";

export interface UserRequest {
    firstName: string;
    lastName: string;
    address: string;
    phone: string;
    imageUrl?: string;
    roles: ERole[];
}

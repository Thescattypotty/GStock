import { ERole } from "./erole";

export interface RegisterRequest {
    username: string;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    address: string;
    phone: string;
    imageUrl?: string;
    roles: ERole[];
}

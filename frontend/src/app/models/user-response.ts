import { ERole } from "./erole";

export interface UserResponse {
    id: string;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    address: string;
    phone: string;
    imageUrl?: string;
    roles: ERole[];
    createdAt: Date;
    updatedAt: Date;
}

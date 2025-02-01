import { ItemCategoryResponse } from "./item-category-response";

export interface InventoryItemResponse {
    id: string;
    name: string;
    description: string;
    quantity: number;
    costPrice: number;
    tva: number;
    category: ItemCategoryResponse;
    createdAt: string;
    updatedAt: string;
    createdBy: string;
    updatedBy: string;
}

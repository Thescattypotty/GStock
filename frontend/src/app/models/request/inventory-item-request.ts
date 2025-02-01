export interface InventoryItemRequest {
    name: string;
    description: string;
    quantity: number;
    costPrice: number;
    tva: number;
    categoryId: string;
}

import { InventoryItemResponse } from "./inventory-item-response";

export interface ProductQuoteResponse {
    id: string;
    item: InventoryItemResponse;
    quantity: number;
    discount: number;
    margeDeGain: number;
    priceHT: number;
    priceTTC: number;
}

package org.gestionstock.stock.Payload.Response;

public record ContactResponse(
    String id,
    String fullName,
    String imageUrl,
    String email,
    String title,
    String phone,
    boolean isCustomer,
    CompanyResponse companyResponse,
    String createdBy,
    String updatedBy,
    String createdAt,
    String updatedAt
){
    
}

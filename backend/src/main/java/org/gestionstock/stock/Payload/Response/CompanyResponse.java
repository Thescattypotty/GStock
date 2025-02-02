package org.gestionstock.stock.Payload.Response;

import java.util.List;

import org.gestionstock.stock.Enum.EBusinessType;
import org.gestionstock.stock.Enum.ECompanySize;
import org.gestionstock.stock.Enum.EIndustry;

public record CompanyResponse(
    String id,
    String name,
    String imageUrl,
    ECompanySize size,
    EIndustry industry,
    EBusinessType businessType,
    String country,
    String city,
    String address,
    String website,
    List<ContactResponse> contacts,
    UserResponse salesOwner,
    String createdAt,
    String updatedAt,
    String createdBy,
    String updatedBy
) {
    
}

package org.gestionstock.stock.Payload.Request;

import java.util.List;

import org.gestionstock.stock.Enum.EBusinessType;
import org.gestionstock.stock.Enum.ECompanySize;
import org.gestionstock.stock.Enum.EIndustry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRequest(
    
    @NotBlank(message = "Name is required")
    String name,
    
    String imageUrl,

    @NotNull(message = "Size is required")
    ECompanySize size,

    @NotNull(message = "Industry is required")
    EIndustry industry,
    
    @NotNull(message = "Business type is required")
    EBusinessType businessType,
    
    @NotBlank(message = "Country is required")
    String country,
    
    @NotBlank(message = "City is required")
    String city,
    
    @NotBlank(message = "Address is required")
    String address,
    
    String website,

    @NotNull(message = "Contacts id is required")
    List<String> contactsId,

    @NotBlank(message = "Sales owner id is required")
    String salesOwnerId
) {
    
}

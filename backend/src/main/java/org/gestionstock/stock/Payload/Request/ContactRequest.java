package org.gestionstock.stock.Payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactRequest(

    @NotBlank(message = "Full name is required")
    String fullName,

    String imageUrl,

    @Email(message = "Email is invalid")
    String email,

    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Phone is required")
    String phone,

    @NotBlank(message = "Company id is required")
    String companyId
) {
    
}

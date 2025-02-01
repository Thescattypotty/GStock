package org.gestionstock.stock.Payload.Request;

import java.util.Set;

import org.gestionstock.stock.Enum.ERole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @NotNull(message = "Missing FirstName")
    @NotBlank(message = "FirstName cannot be blank")
    String firstName,

    @NotNull(message = "Missing LastName")
    @NotBlank(message = "LastName cannot be blank")
    String lastName,

    @NotNull(message = "Missing Address")
    @NotBlank(message = "Address cannot be blank")
    String address,

    @NotNull(message = "Missing Phone")
    @NotBlank(message = "Phone cannot be blank")
    String phone,

    String imageUrl,
    
    @NotNull(message = "Missing Roles")
    Set<ERole> roles
) {
    
}

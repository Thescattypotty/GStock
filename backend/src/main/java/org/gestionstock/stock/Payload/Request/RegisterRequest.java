package org.gestionstock.stock.Payload.Request;

import java.util.Set;

import org.gestionstock.stock.Enum.ERole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
    @NotNull(message = "Username is required")
    @NotBlank(message = "Username is required")
    String username,

    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    String email,

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    String password,

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    String firstName,

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    String lastName,

    @NotNull(message = "Address is required")
    @NotBlank(message = "Address is required")
    String address,

    @NotNull(message = "Phone is required")
    @NotBlank(message = "Phone is required")
    String phone,

    String imageUrl,

    @NotNull(message = "Roles is required")
    Set<ERole> roles
) {
    
}

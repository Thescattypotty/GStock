package org.gestionstock.stock.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    String username,

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    String password
) {
    
}

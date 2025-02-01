package org.gestionstock.stock.Payload.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(
    @NotNull(message = "Old password is required")
    @NotBlank(message = "Old password is required")
    String oldPassword,
    
    @NotNull(message = "New password is required")
    @NotBlank(message = "New password is required")
    String newPassword
) {
    
}

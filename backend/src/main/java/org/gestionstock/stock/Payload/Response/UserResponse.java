package org.gestionstock.stock.Payload.Response;

import java.util.Set;

import org.gestionstock.stock.Enum.ERole;

public record UserResponse(
    String id,
    String username,
    String email,
    String firstName,
    String lastName,
    String address,
    String phone,
    String imageUrl,
    Set<ERole> roles,
    String createdAt,
    String updatedAt
) {
    
}

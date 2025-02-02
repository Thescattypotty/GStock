package org.gestionstock.stock.Payload.Mapper;

import java.time.format.DateTimeFormatter;

import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.Payload.Request.RegisterRequest;
import org.gestionstock.stock.Payload.Response.UserResponse;
import org.springframework.stereotype.Service;

@Service(value = "UserMapper")
public class UserMapper {
    
    public static User toUser(RegisterRequest registerRequest){
        return User.builder()
            .username(registerRequest.username())
            .email(registerRequest.email())
            .password(registerRequest.password())
            .firstName(registerRequest.firstName())
            .lastName(registerRequest.lastName())
            .address(registerRequest.address())
            .phone(registerRequest.phone())
            .imageUrl(registerRequest.imageUrl())
            .roles(registerRequest.roles())
            .build();
    }

    public static UserResponse fromUser(User user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new UserResponse(
            user.getId().toString(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getAddress(),
            user.getPhone(),
            user.getImageUrl(),
            user.getRoles(),
            user.getCreatedAt().format(formatter),
            user.getUpdatedAt().format(formatter)
        );
    }
}

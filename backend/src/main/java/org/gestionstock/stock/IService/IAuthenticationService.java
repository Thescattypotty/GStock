package org.gestionstock.stock.IService;

import org.gestionstock.stock.Payload.Request.LoginRequest;
import org.gestionstock.stock.Payload.Response.JwtResponse;
import org.gestionstock.stock.Payload.Response.UserResponse;

public interface IAuthenticationService {
    JwtResponse login(LoginRequest loginRequest);
    UserResponse currentUser();
}

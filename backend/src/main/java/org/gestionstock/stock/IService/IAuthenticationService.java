package org.gestionstock.stock.IService;

import org.gestionstock.stock.Payload.Request.LoginRequest;
import org.gestionstock.stock.Payload.Response.JwtResponse;

public interface IAuthenticationService {

    JwtResponse login(LoginRequest loginRequest);
}

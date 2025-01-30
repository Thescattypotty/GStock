package org.gestionstock.stock.Service;

import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.IService.IAuthenticationService;
import org.gestionstock.stock.Payload.Request.LoginRequest;
import org.gestionstock.stock.Payload.Response.JwtResponse;
import org.gestionstock.stock.Util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService{
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())  
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String jwt = "Bearer " + jwtUtil.generateToken(user.getUsername(), user.getAuthorities());

        return new JwtResponse(jwt);
    }   
}

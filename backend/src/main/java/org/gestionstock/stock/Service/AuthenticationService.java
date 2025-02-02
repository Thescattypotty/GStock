package org.gestionstock.stock.Service;

import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.Exception.InvalidCredentialsException;
import org.gestionstock.stock.IService.IAuthenticationService;
import org.gestionstock.stock.Payload.Mapper.UserMapper;
import org.gestionstock.stock.Payload.Request.LoginRequest;
import org.gestionstock.stock.Payload.Response.JwtResponse;
import org.gestionstock.stock.Payload.Response.UserResponse;
import org.gestionstock.stock.Util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService{
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        log.info("Request to login user: {}", loginRequest);
        Authentication authentication = null;
        try {
            log.info("Trying to authenticate user: {}", loginRequest.username());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials for user: {}", loginRequest.username());
            throw new InvalidCredentialsException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("User: {} has been authenticated", authentication.getPrincipal().toString());

        User user = (User) authentication.getPrincipal();
        log.info("User: {} has been Getted", user.getUsername());

        String jwt = jwtUtil.generateToken(user.getUsername(), user.getAuthorities());
        log.info("JWT: {} has been generated", jwt);
        return new JwtResponse(jwt);
    }

    @Override
    public UserResponse currentUser() {
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return UserMapper.fromUser(user);
    }   
}

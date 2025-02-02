package org.gestionstock.stock.Controller;

import org.gestionstock.stock.Payload.Request.LoginRequest;
import org.gestionstock.stock.Payload.Response.JwtResponse;
import org.gestionstock.stock.Payload.Response.UserResponse;
import org.gestionstock.stock.Service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        log.info("Request to login user: {}", loginRequest);
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> currentUser(){
        return new ResponseEntity<>(authenticationService.currentUser(), HttpStatus.OK);
    }
}

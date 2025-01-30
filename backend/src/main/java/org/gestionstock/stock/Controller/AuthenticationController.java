package org.gestionstock.stock.Controller;

import org.gestionstock.stock.Payload.Request.LoginRequest;
import org.gestionstock.stock.Payload.Response.JwtResponse;
import org.gestionstock.stock.Service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return new ResponseEntity<>(authenticationService.login(loginRequest), HttpStatus.OK);
    }
}

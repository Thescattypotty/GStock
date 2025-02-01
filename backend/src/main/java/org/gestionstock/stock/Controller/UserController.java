package org.gestionstock.stock.Controller;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ChangePasswordRequest;
import org.gestionstock.stock.Payload.Request.ChangeUsernameRequest;
import org.gestionstock.stock.Payload.Request.RegisterRequest;
import org.gestionstock.stock.Payload.Request.UserRequest;
import org.gestionstock.stock.Payload.Response.UserResponse;
import org.gestionstock.stock.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SUPER_ADMINISTRATEUR')")
public class UserController {
    
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid RegisterRequest registerRequest){
        userService.createUser(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String id ,@RequestBody @Valid UserRequest userRequest){
        userService.updateUser(id, userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable("id") String id ,@RequestBody @Valid ChangePasswordRequest changePasswordRequest){
        userService.changePassword(id, changePasswordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}/change-username")
    public ResponseEntity<Void> changeUsername(@PathVariable("id") String id ,@RequestBody @Valid ChangeUsernameRequest changeUsernameRequest){
        userService.changeUsername(id, changeUsernameRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") String id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }



}

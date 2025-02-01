package org.gestionstock.stock.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gestionstock.stock.Entity.User;
import org.gestionstock.stock.EntityRepository.UserRepository;
import org.gestionstock.stock.Exception.InvalidCredentialsException;
import org.gestionstock.stock.Exception.UserNotFoundException;
import org.gestionstock.stock.Exception.UsernameAlreadyExistException;
import org.gestionstock.stock.IService.IUserService;
import org.gestionstock.stock.Payload.Mapper.UserMapper;
import org.gestionstock.stock.Payload.Request.ChangePasswordRequest;
import org.gestionstock.stock.Payload.Request.ChangeUsernameRequest;
import org.gestionstock.stock.Payload.Request.RegisterRequest;
import org.gestionstock.stock.Payload.Request.UserRequest;
import org.gestionstock.stock.Payload.Response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void createUser(RegisterRequest RegusterRequest) {
        User user = userMapper.toUser(RegusterRequest);
        log.info("User: {}", user);
        user.setPassword(passwordEncoder.encode(RegusterRequest.password()));
        userRepository.save(user);
        log.info("User saved");
    }

    @Override
    @Transactional
    public void updateUser(String id, UserRequest userRequest) {
        User user = userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new UserNotFoundException());
        log.info("User: {}", user);
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setAddress(userRequest.address());
        user.setPhone(userRequest.phone());
        user.setImageUrl(userRequest.imageUrl());
        user.setRoles(userRequest.roles());
        userRepository.save(user);
        log.info("User updated");
    }

    @Override
    @Transactional
    public void changePassword(String id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new UserNotFoundException());
        log.info("User: {}", user);
        if(!passwordEncoder.matches(changePasswordRequest.oldPassword(), user.getPassword())){
            log.info("Invalid Password Request");
            throw new InvalidCredentialsException();
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        userRepository.save(user);
        log.info("Password changed");
    }

    @Override
    @Transactional
    public void changeUsername(String id, ChangeUsernameRequest changeUsernameRequest) {
        User user = userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new UserNotFoundException());
        log.info("User: {}", user);
        if(userRepository.existsByUsername(changeUsernameRequest.newUsername())){
            log.info("Username already exist");
            throw new UsernameAlreadyExistException();
        }
        user.setUsername(changeUsernameRequest.newUsername());
        userRepository.save(user);
        log.info("Username changed");
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        if(!userRepository.existsById(UUID.fromString(id))){
            log.info("User not found");
            throw new UserNotFoundException();
        }
        userRepository.deleteById(UUID.fromString(id));
        log.info("User deleted");
    }

    @Override
    public UserResponse getUser(String id) {
        log.info("User id: {}", id);
        return userRepository.findById(UUID.fromString(id))
            .map(userMapper::fromUser)
            .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("All users");
        return userRepository.findAll()
            .stream()
            .map(userMapper::fromUser)
            .collect(Collectors.toList());
    }
    
}

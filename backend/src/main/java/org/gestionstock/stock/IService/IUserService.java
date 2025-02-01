package org.gestionstock.stock.IService;

import java.util.List;

import org.gestionstock.stock.Payload.Request.ChangePasswordRequest;
import org.gestionstock.stock.Payload.Request.ChangeUsernameRequest;
import org.gestionstock.stock.Payload.Request.RegisterRequest;
import org.gestionstock.stock.Payload.Request.UserRequest;
import org.gestionstock.stock.Payload.Response.UserResponse;

public interface IUserService {
    void createUser(RegisterRequest RegusterRequest);
    void updateUser(String id, UserRequest userRequest);
    void changePassword(String id, ChangePasswordRequest changePasswordRequest);
    void changeUsername(String id, ChangeUsernameRequest changeUsernameRequest);
    void deleteUser(String id);
    UserResponse getUser(String id);
    List<UserResponse> getAllUsers();
}

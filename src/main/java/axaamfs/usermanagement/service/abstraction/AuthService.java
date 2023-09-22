package axaamfs.usermanagement.service.abstraction;

import axaamfs.usermanagement.dto.TokenResponse;
import axaamfs.usermanagement.dto.LoginUserRequest;
import axaamfs.usermanagement.entity.User;

public interface AuthService {
//Abstraction
    TokenResponse login(LoginUserRequest request);

    void logout(User user);
}

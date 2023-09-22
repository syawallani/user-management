package axaamfs.usermanagement.service.implementation;

import axaamfs.usermanagement.dto.TokenResponse;
import axaamfs.usermanagement.dto.LoginUserRequest;
import axaamfs.usermanagement.entity.User;
import axaamfs.usermanagement.repository.UserRepository;
import axaamfs.usermanagement.service.ValidationService;
import axaamfs.usermanagement.service.abstraction.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validator;

    @Override
    @Transactional
    public TokenResponse login(LoginUserRequest request) {

        validator.validate(request);

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(System.currentTimeMillis() + (1000 * 60 * 30));

            userRepository.save(user);
            return new TokenResponse(user.getToken(), user.getTokenExpiredAt());

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }

    @Override
    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }
}

package axaamfs.usermanagement.service.implementation;

import axaamfs.usermanagement.dto.RegisterUserRequest;
import axaamfs.usermanagement.dto.SearchAccountRequest;
import axaamfs.usermanagement.dto.UpdateUserRequest;
import axaamfs.usermanagement.dto.UserResponse;
import axaamfs.usermanagement.entity.User;
import axaamfs.usermanagement.repository.UserRepository;
import axaamfs.usermanagement.service.ValidationService;
import axaamfs.usermanagement.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validator;

    @Override
    @Transactional
    public void register(RegisterUserRequest request) {
        validator.validate(request);

        Optional<User> nullableUser = userRepository.findByUsername(request.getUsername());

        if (nullableUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(user.getName());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);
    }

    @Override
    public UserResponse get(User user) {
        return new UserResponse(user.getUsername(), user.getName());
    }

    @Override
    public UserResponse update(User user, UpdateUserRequest request) {
        validator.validate(request);

        if (Objects.nonNull(request.getUsername())) {
            if (!request.getUsername().equals(user.getUsername())) {
                Optional<User> nullableUser = userRepository.findByUsername(request.getUsername());
                if (nullableUser.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already registered");
                } else {
                    user.setUsername(request.getUsername());
                }
            }
        }

        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }
        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);
        return new UserResponse(user.getUsername(), user.getName());
    }

    @Override
    public Page<UserResponse> search(SearchAccountRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return userRepository.findAllUser(request.getFilter(), pageable);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found"));
        userRepository.delete(user);
        //Hard delete
    }


}

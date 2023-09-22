package axaamfs.usermanagement.service.abstraction;

import axaamfs.usermanagement.dto.RegisterUserRequest;
import axaamfs.usermanagement.dto.SearchAccountRequest;
import axaamfs.usermanagement.dto.UpdateUserRequest;
import axaamfs.usermanagement.dto.UserResponse;
import axaamfs.usermanagement.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    void register(RegisterUserRequest request);

    UserResponse get(User user);

    UserResponse update(User user, UpdateUserRequest request);

    Page<UserResponse> search(SearchAccountRequest request);

    void deleteByUsername(String username);


}

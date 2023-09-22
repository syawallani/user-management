package axaamfs.usermanagement.controller;

import axaamfs.usermanagement.dto.*;
import axaamfs.usermanagement.entity.User;
import axaamfs.usermanagement.service.abstraction.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        WebResponse<String> response = new WebResponse<>();
        response.setData("OK");
        log.info("Register user {}", request.getUsername());
        return response;
    }

    @GetMapping(
            path = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);
        WebResponse<UserResponse> response = new WebResponse<>();
        response.setData(userResponse);
        log.info("Current user {}", userResponse.getUsername());
        return response;
    }

    @PatchMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.update(user, request);
        WebResponse<UserResponse> response = new WebResponse<>();
        response.setData(userResponse);
        log.info("Update user {}", request.getUsername());
        return response;
    }

    @GetMapping(
            path = "/list-user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<UserResponse>> listUser(@RequestParam(required = false, defaultValue = "") String filterRequest,
                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(required = false, defaultValue = "10") Integer size) {

        SearchAccountRequest request = new SearchAccountRequest(filterRequest, page, size);
        Page<UserResponse> userResponsePage = userService.search(request);

        WebResponse<List<UserResponse>> response = new WebResponse<>();
        response.setData(userResponsePage.getContent());
        response.setPaging(new PagingResponse(
                userResponsePage.getNumber(),
                userResponsePage.getTotalPages(),
                userResponsePage.getSize()));
        log.info("Get List User");
        return response;
    }

    @DeleteMapping(
            path = "/delete/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("username") String username){
        userService.deleteByUsername(username);
        log.info("Delete user {}", username);
        return new WebResponse("OK", null, null);
    }
}

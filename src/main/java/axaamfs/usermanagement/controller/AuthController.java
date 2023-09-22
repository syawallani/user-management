package axaamfs.usermanagement.controller;

import axaamfs.usermanagement.dto.LoginUserRequest;
import axaamfs.usermanagement.dto.TokenResponse;
import axaamfs.usermanagement.dto.WebResponse;
import axaamfs.usermanagement.entity.User;
import axaamfs.usermanagement.service.abstraction.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request){
        TokenResponse tokenResponse = authService.login(request);
        WebResponse response = new WebResponse<>();
        response.setData(tokenResponse);
        log.info("Login  user {}", request.getUsername());
        return response;
    }

    @DeleteMapping(
            path = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user){
        authService.logout(user);
        WebResponse response = new WebResponse<>();
        response.setData("OK");
        log.info("Logout user {}", user.getUsername());
        return response;
    }



}

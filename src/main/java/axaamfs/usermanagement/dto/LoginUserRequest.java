package axaamfs.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginUserRequest {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String username;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String password;

    public LoginUserRequest() {
    }

    public LoginUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

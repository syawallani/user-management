package axaamfs.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String username;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String password;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String name;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

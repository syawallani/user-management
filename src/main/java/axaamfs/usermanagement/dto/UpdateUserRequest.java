package axaamfs.usermanagement.dto;

import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 100)
    private String username;

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String password;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

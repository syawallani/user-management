package axaamfs.usermanagement.dto;

public class UserResponse {

    public String username;

    public String name;

    public UserResponse() {
    }

    public UserResponse(String username, String name) {
        this.username = username;
        this.name = name;
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
}

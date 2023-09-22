package axaamfs.usermanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String name;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    public User() {
    }

    public User(String username, String password, String name, String token, Long tokenExpiredAt) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.token = token;
        this.tokenExpiredAt = tokenExpiredAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpiredAt() {
        return tokenExpiredAt;
    }

    public void setTokenExpiredAt(Long tokenExpiredAt) {
        this.tokenExpiredAt = tokenExpiredAt;
    }
}

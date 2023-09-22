package axaamfs.usermanagement.dto;

public class TokenResponse {

    private String token;

    private Long tokenExpiredAt;

    public TokenResponse(String token, Long tokenExpiredAt) {
        this.token = token;
        this.tokenExpiredAt = tokenExpiredAt;
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

package org.acme;

public class LoginResponse {
    private Long userId;
    private String username;
    private String token;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(Long userId, String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }


    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

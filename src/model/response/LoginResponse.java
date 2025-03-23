package model.response;

import model.entity.Role;
import model.entity.User;

public class LoginResponse {
    private boolean success;
    private  String message;
    private User user;

    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", user=" + user +
                '}';
    }
}

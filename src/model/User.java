package model;

public class User extends Person {
    private String username;
    private String password;
    private String role; // "admin" hoặc "customer"

    public User() {
        super(); // Gọi constructor mặc định của Person
    }

    // Constructor có tham số (gọi constructor của Person)
    public User(int id,String name, String email, String phone, String address,String username, String password, String role) {
        super(id,name, email, phone, address);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


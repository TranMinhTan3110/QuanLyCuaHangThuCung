package model.entity;

public class User extends Person {
    private String userName;
    private String password;
    private Role role; // "admin" hoặc "customer"


    public User() {
        super(); // Gọi constructor mặc định của Person
    }

    // Constructor có tham số (gọi constructor của Person)
    public User(int id,String name,  String phone, String address,String username, String password, Role role) {
        super(id,name, phone, address);
        this.userName = username;
        this.password = password;
        this.role = role;
    }



    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


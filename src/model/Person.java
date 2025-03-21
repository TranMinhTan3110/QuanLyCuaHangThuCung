package model;

public class Person {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public   Person(){
        this.id = 0;
        this.name = "";
        this.address = "";
        this.email = "";
        this.phone = "";
    }

    public Person(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

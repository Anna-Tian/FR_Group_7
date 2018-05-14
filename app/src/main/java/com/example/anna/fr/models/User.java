package com.example.anna.fr.models;

public class User {
    private String user_id;
    private long phone;
    private String email;
    private String username;
    private String description;
    private String address;

    public User(String user_id, long phone, String email, String username, String description, String address) {
        this.user_id = user_id;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.description = description;
        this.address = address;
    }

    public User(){

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

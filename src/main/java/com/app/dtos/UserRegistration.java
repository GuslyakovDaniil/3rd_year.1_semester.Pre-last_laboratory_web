package com.app.dtos;

import jakarta.validation.constraints.*;

public class UserRegistration {
    private String username;
    private String first_name;
    private String email;
    private String last_name;
    private String password;
    private String confirmPassword;

    public UserRegistration() {}

    @NotEmpty(message = "User name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @NotEmpty(message = "User name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "User name cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    @NotEmpty(message = "Email cannot be null or empty!")
    @Email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Password cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotEmpty(message = "Confirm Password cannot be null or empty!")
    @Size(min = 5, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "username='" + username + '\'' +
                ", first name='" + first_name + '\'' +
                ", email='" + email + '\'' +
                ", last name=" + last_name +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

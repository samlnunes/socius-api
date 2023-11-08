package com.api.socius.dtos;

import jakarta.validation.constraints.*;

public class UserDto {
    @NotBlank(message = "O campo 'username' não deve estar em branco")
    private String username;

    @NotBlank(message = "O campo 'name' não deve estar em branco")
    private String name;

    @NotBlank(message = "O campo 'lastname' não deve estar em branco")
    private String lastname;

    @NotBlank(message = "O campo 'email' não deve estar em branco")
    @Email
    private String email;

    @NotBlank(message = "O campo 'password' não deve estar em branco")
    private String password;

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.buutcamp.controller.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewUser {

    @NotNull
    @Size(min=3)
    private String userName;

    //@Pattern(regexp="")

    @NotNull
    @Size(min=5)
    private String password;

    private String role;

    public NewUser(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

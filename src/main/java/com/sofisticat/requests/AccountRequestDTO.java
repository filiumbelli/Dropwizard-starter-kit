package com.sofisticat.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public record AccountRequestDTO(@JsonProperty("username") @NotEmpty  String username,
                                @JsonProperty("password") @NotEmpty  String password) {

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "AccountRequestDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package com.sofisticat.storage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.UUID;

public class Account implements Principal {

    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    @JsonIgnore
    private UUID id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @VisibleForTesting
    public Account(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Account() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return this.username;
    }


}

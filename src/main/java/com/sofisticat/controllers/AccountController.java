package com.sofisticat.controllers;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sofisticat.util.Hasher;
import com.sofisticat.managers.AccountsManager;
import com.sofisticat.requests.AccountRequestDTO;
import com.sofisticat.storage.models.Account;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.Optional;

@Path("/api/accounts")
public class AccountController {

    private final AccountsManager accountsManager;
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    public AccountController(AccountsManager accountsManager) {
        this.accountsManager = accountsManager;
    }

    @Timed
    @POST
    @Path("/login")
    public Response postLoginRequest(@Valid AccountRequestDTO request) throws JoseException, JsonProcessingException {
        if (Objects.isNull(request.getUsername()) || Objects.isNull(request.getPassword())) {
            throw new WebApplicationException(Response.status(500).build());
        }
        Optional<Account> account = accountsManager.get(request.getUsername());

        if (account.isEmpty()) {
            throw new WebApplicationException(Response.status(400).build());
        }
        MessageDigest md = Hasher.getMessageDigestSHA512();
        assert md != null;
        byte[] password = md.digest(request.getPassword().getBytes(StandardCharsets.UTF_8));
        if(password.toString().equals(account.get().getPassword())) {
            throw new WebApplicationException(Response.status(400).build());
        }
        return Response.status(200).build();
    }

    @Timed
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRegisterRequest(@Valid AccountRequestDTO request) throws JoseException, JsonProcessingException {
        if (Objects.isNull(request.getUsername()) || Objects.isNull(request.getPassword())) {
            throw new WebApplicationException(Response.status(500).build());
        }
        if (accountsManager.get(request.getUsername()).isPresent()) {
            throw new WebApplicationException(Response.status(409).build());
        }
        accountsManager.createAccount(request.getUsername(), request.getPassword());
        return Response.status(200).build();
    }
}

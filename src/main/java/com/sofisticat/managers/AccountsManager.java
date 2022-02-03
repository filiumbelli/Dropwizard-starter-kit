package com.sofisticat.managers;

import com.sofisticat.util.Hasher;
import com.sofisticat.storage.Accounts;
import com.sofisticat.storage.models.Account;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.UUID;

public class AccountsManager {

    private final Accounts accounts;

    public AccountsManager(Accounts accounts) {
        this.accounts = accounts;
    }


    public Optional<Account> get(String username) {
        return accounts.getByUsername(username);
    }

    public boolean createAccount(String username, String password) {
        MessageDigest md = Hasher.getMessageDigestSHA512();
        assert md != null;
        byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return accounts.createAccount(UUID.randomUUID(), username, digest.toString());
    }
}

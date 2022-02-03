package com.sofisticat.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofisticat.storage.mappers.AccountRowMapper;
import com.sofisticat.storage.models.Account;
import com.sofisticat.util.SystemMapper;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;
import java.util.UUID;

public class Accounts {
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private static final ObjectMapper mapper = SystemMapper.getMapper();
    private final Jdbi database;

    public Accounts(Jdbi database) {
        this.database = database;
        this.database.registerRowMapper(new AccountRowMapper());
    }

    public Optional<Account> getByUsername(String username) {
        String query = "SELECT * FROM accounts WHERE " + USERNAME + "=:username";
        return this.database.withHandle(handle -> handle.createQuery(query)
                .bind("username", username)
                .mapToBean(Account.class)
                .findOne());
    }

    public boolean createAccount(UUID uuid, String username, String password) {
        String query = "INSERT INTO accounts(" + ID + ", " + USERNAME + ", " + PASSWORD + ") VALUES(:id, :username,:password)";
        return this.database.withHandle(handle ->
                handle.createUpdate(query)
                        .bind("id", uuid)
                        .bind("username", username)
                        .bind("password", password).execute() > 0
        );
    }
//    public boolean create(Account account) {
//        return this.database.withHandle(i -> i.inTransaction(TransactionIsolationLevel.SERIALIZABLE,handle -> {
//            UUID uuid = handle.createQuery("INSERT INTO accounts (" + USERNAME + "," + PASSWORD + ") VALUES(:username,:password) ON CONFLICT(username) DO NOTHING")
//                    .bind("username",account.getUsername())
//                    .bind("password",account.getPassword())
//                    .mapTo(UUID.class)
//                    .findFirst().get();
//        }));
//    }


}

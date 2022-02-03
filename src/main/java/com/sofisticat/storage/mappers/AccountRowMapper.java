package com.sofisticat.storage.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofisticat.storage.Accounts;
import com.sofisticat.storage.models.Account;
import com.sofisticat.util.SystemMapper;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountRowMapper implements RowMapper<Account> {
    private static ObjectMapper mapper = SystemMapper.getMapper();

    @Override
    public Account map(ResultSet resultSet, StatementContext context) throws SQLException {
        Account account = new Account();
        account.setId(mapper.convertValue(resultSet.getObject(Accounts.ID), UUID.class));
        account.setUsername(resultSet.getString(Accounts.USERNAME));
        account.setPassword(resultSet.getString(Accounts.PASSWORD));
        return account;
    }
}

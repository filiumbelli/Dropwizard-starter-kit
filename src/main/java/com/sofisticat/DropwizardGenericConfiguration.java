package com.sofisticat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sofisticat.configuration.AccountDatabaseConfiguration;
import com.sofisticat.configuration.RedisConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DropwizardGenericConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private AccountDatabaseConfiguration accountdb;

    @Valid
    @NotNull
    @JsonProperty
    private RedisConfiguration redisConfiguration;

    @JsonProperty("redis")
    public RedisConfiguration getRedisConfiguration() {
        return redisConfiguration;
    }

    @JsonProperty("accountdb")
    public AccountDatabaseConfiguration getAccountDatabaseConfiguration() {
        return accountdb;
    }

}

package com.sofisticat.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class RedisConfiguration {
    @JsonProperty
    @NotEmpty
    private String url;

    @JsonProperty
    @NotEmpty
    private String port;

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }

}

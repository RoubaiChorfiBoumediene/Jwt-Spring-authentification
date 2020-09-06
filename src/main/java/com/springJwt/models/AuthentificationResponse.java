package com.springJwt.models;

public class AuthentificationResponse {
    private String jwt;

    public AuthentificationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

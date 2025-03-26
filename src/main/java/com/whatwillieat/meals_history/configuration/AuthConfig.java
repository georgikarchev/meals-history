package com.whatwillieat.meals_history.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    private static String authToken;

    @Value("${app.api-key}")
    public void setAuthToken(String token) {
        authToken = token;
    }

    public static String getAuthToken() {
        return authToken;
    }
}
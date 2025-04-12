package com.example.demo.util;

import com.example.demo.exception.UnauthorizedException;

public class AuthorizationUtils {

    private AuthorizationUtils() {
    }

    public static String extractUserIdFromAuthorization(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException();
        }
        return authorization.substring(7);
    }

}

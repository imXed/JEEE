package com.club.escalade.service;

public interface PasswordResetService {
    void requestReset(String email, String appBaseUrl);

    boolean isValidToken(String token);

    boolean resetPassword(String token, String newPassword);
}


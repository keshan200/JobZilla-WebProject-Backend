package lk.ijse.jobzillabackend.service;

public interface PasswordResetTokenService {

    String forgotPassword(String email);
    String resetPassword(String otp, String newPassword);
}

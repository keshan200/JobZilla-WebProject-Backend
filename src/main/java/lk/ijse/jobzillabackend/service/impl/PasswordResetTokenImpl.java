package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.email.EmailService;
import lk.ijse.jobzillabackend.entity.PasswordResetToken;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.PasswordResetTokenRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PasswordResetTokenImpl implements PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public String forgotPassword(String email) {
        User user = userRepository.findByEmailNative(email);
        if (user == null) {
            throw new RuntimeException("No user found with the provided email.");
        }

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        tokenRepository.deleteByUser(user);

        PasswordResetToken token = new PasswordResetToken();
        token.setOtp(otp);
        token.setExpirationTime(LocalDateTime.now().plusMinutes(10));
        token.setUser(user);
        tokenRepository.save(token);

        emailService.sendEmail(user.getEmail(), "Password Reset OTP", "Your OTP is: " + otp);

        return "OTP sent to your email.";
    }

    @Override
    @Transactional
    public String resetPassword(String otp, String newPassword) {
        PasswordResetToken token = tokenRepository.findByOtp(otp)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));

        if (token.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(token);

        return "Password reset successful.";
    }
}



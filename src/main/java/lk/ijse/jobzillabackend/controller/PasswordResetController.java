package lk.ijse.jobzillabackend.controller;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.email.EmailService;
import lk.ijse.jobzillabackend.entity.PasswordResetToken;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.PasswordResetTokenRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.PasswordResetTokenService;
import lk.ijse.jobzillabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

private final PasswordResetTokenService passwordResetTokenService;

    public PasswordResetController(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping("/forgot-password/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email) {
        String response = passwordResetTokenService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String otp, @RequestParam String newPassword) {
        String response = passwordResetTokenService.resetPassword(otp, newPassword);
        return ResponseEntity.ok(response);
    }
}

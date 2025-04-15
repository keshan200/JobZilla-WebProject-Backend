package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.PasswordResetToken;
import lk.ijse.jobzillabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByOtp(String otp);
    void deleteByUser(User user);
}

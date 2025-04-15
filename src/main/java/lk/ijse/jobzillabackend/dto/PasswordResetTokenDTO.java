package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lk.ijse.jobzillabackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordResetTokenDTO {


    private Long id;

    private String otp;

    private String expirationTime;

    private UserDTO user;
}

package lk.ijse.jobzillabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthDTO {
    private UUID id;
    private String email;
    private String token;
    private String refreshToken;

}

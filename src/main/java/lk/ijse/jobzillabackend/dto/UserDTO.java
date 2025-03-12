package lk.ijse.jobzillabackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email Address")
    private String email;


    private String password;

    @NotBlank(message = "username is required")
    private String username;
    private String role;
}

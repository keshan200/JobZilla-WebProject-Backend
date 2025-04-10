package lk.ijse.jobzillabackend.dto;

import jakarta.validation.constraints.*;
import lk.ijse.jobzillabackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserDTO{

    private UUID uid;


    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "username is required")
    private String username;

    @NotNull(message = "Mobile number is required")
    private String mobile;

    @NotNull(message = "role must be required")
    @Pattern(regexp = "^(EMPLOYER|CANDIDATE)$", message = "Role must be either EMPLOYER or CANDIDATE")
    private String role;

    private Status status ;


    public UserDTO(UUID uid, String email, int mobile) {
        this.uid = uid;
        this.email = email;
        this.mobile = String.valueOf(mobile);
    }
}

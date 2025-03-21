package lk.ijse.jobzillabackend.dto;

import jakarta.validation.constraints.*;
import lk.ijse.jobzillabackend.entity.User;
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

    @NotBlank(message = "email is required")
    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "username is required")
    private String username;

    @NotNull(message = "Mobile number is required")
    private int mobile;

    @NotNull(message = "role must be required")
    @Pattern(regexp = "^(EMPLOYER|CANDIDATE)$", message = "Role must be either EMPLOYER or CANDIDATE")
    private String role;

    private Status status ;





}

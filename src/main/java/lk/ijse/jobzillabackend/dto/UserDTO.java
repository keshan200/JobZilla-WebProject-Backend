package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.util.UUIDDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements Serializable {

    @JsonDeserialize(using = UUIDDeserializer.class)
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
    private String role;

    private boolean status = true;


}

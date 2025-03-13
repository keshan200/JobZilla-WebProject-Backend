package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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


    private String password;

    @NotBlank(message = "username is required")
    private String username;
    private String role;


}

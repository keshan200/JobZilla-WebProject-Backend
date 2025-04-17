package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lk.ijse.jobzillabackend.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SocialMediaDTO {


    private int sid;

    @Pattern(regexp = "WhatsApp|Facebook|LinkedIn|Instagram", message = "Invalid platform. Allowed values are: WhatsApp, Facebook, LinkedIn, Instagram.")
    private String platform;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL must start with http:// or https://")
    private String url;


    private UserDTO user;
}

package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.util.UUIDDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SocialMediaDTO {

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID sid;
    private String platform;
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must start with http:// or https://")
    private String url;


}

package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.util.UUIDDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyDTO {

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID cid;

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String company_name;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country name cannot exceed 50 characters")
    private String country;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name cannot exceed 50 characters")
    private String city;

    @NotBlank(message = "Full address is required")
    @Size(max = 255, message = "Full address cannot exceed 255 characters")
    private String full_address;

    @NotNull(message = "Mobile number is required")
    @Min(value = 1000000000, message = "Mobile number must be at least 10 digits")
    @Max(value = 9999999999L, message = "Mobile number cannot exceed 10 digits")
    private int mobile_number;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters  ")
    private String description;


    private String logo_img;


    private String background_img;


    @Pattern(regexp = "\\d{4}", message = "Establishment year must be a 4-digit number")
    private String est_since;


    private User user;
}

package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lk.ijse.jobzillabackend.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CompanyDTO {


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

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters  ")
    private String description;

    @Pattern(regexp = "^(http|https)://.*$", message = "Website must be a valid URL")
    private String website;


    private String logo_img;

    private String background_img;

    private List<String> image_collection = new ArrayList<>();

    @Pattern(regexp = "\\d{4}", message = "Establishment year must be a 4-digit number")
    private String est_since;


    private String company_type;


    private UserDTO user;



    private List<JobDTO> jobs;


}

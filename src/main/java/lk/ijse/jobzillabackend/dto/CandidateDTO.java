package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.jobzillabackend.entity.Application;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CandidateDTO {

    private UUID cand_id;

    private String img;

    @NotBlank(message = "Name must be required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Age must be required")
    private int age;


    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    private String experience;

    @Pattern(regexp = "^(http|https)://.*$", message = "Website must be a valid URL")
    private String website;

    @NotBlank(message = "Country must be required")
    private String country;

    @NotBlank(message = "city must be required")
    private String city;

    @Size(max = 255, message = "Full address must not exceed 255 characters")
    private String full_address;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private List<String> skills = new ArrayList<>();




    private UserDTO user;

    private List<ApplicationDTO> applications;

}

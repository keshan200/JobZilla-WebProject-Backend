package lk.ijse.jobzillabackend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.jobzillabackend.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QualificationDTO {


    private UUID qul_id;

    @NotBlank(message = "Qualification name cannot be blank.")
    @Size(max = 100, message = "Qualification name must be less than 100 characters.")
    private String qul_name;

    @NotBlank(message = "University name cannot be blank.")
    @Size(max = 100, message = "University name must be less than 100 characters.")
    private String university;

    @NotBlank(message = "Year cannot be blank.")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be a valid four-digit year between 1900 and 2099.")
    private String year;

    @Size(max = 500, message = "Description must be less than 500 characters.")
    private String description;

    private Candidate candidate;


}

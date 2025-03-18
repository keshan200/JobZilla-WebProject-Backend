package lk.ijse.jobzillabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotNull(message = "Qualification ID cannot be null")
    private UUID qul_id;

    @NotBlank(message = "Qualification name is required")
    @Size(max = 100, message = "Qualification name must not exceed 100 characters")
    private String qualificationName;

    @NotBlank(message = "Institution name is required")
    @Size(max = 100, message = "Institution name must not exceed 100 characters")
    private String institution;

    @PastOrPresent(message = "Year of completion must be in the past or the current year")
    private int yearOfCompletion;

    @Size(max = 50, message = "Grade must not exceed 50 characters")
    private String grade;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private Candidate candidate;
}

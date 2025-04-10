package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class ApplicationDTO{

     private UUID id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 500, message = "Message must be up to 500 characters")
    private String message;

    @NotBlank(message = "Resume path cannot be empty")
    private String resume;

    @NotNull(message = "Date cannot be null")
    private String date;

    private ApplicationStatus status;


    private JobDTO job;

    private CandidateDTO candidate;




}

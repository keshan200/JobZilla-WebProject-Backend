package lk.ijse.jobzillabackend.dto;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.*;
import jdk.jfr.Category;
import lk.ijse.jobzillabackend.entity.Application;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.entity.JobCategory;
import lk.ijse.jobzillabackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data



public class JobDTO {

    private UUID jobId;

    @NotBlank(message = "Job title is required")
    @Size(max = 100, message = "Job title must not exceed 100 characters")
    private String jobTitle;

    @NotBlank(message = "Job category is required")
    @Size(max = 50, message = "Job category must not exceed 50 characters")
    private String jobCategory;

    @NotBlank(message = "Job type is required")
 /*   @Pattern(regexp = "Full-Time|Part-Time|Contract|Freelance", message = "Invalid job type")*/
    private String jobType;

    @NotBlank(message = "Offered salary is required")
  /*  @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Invalid salary format")*/
    private String offeredSalary;

    @NotBlank(message = "Experience is required")
    @Size(max = 50, message = "Experience description must not exceed 50 characters")
    private String experience;


    @NotBlank(message = "Gender is required")
   /* @Pattern(regexp = "Male|Female|Other", message = "Invalid gender value")*/
    private String gender;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country name must not exceed 50 characters")
    private String country;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City name must not exceed 50 characters")
    private String city;

    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location must not exceed 100 characters")
    private String location;


    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @URL(message = "Invalid website URL")
    private String website;

   /* @Pattern(regexp = "\\d{4}", message = "Invalid establishment year format")*/
    @NotBlank(message = "ests is required")
    private String est_since;

    @NotBlank(message = "Job description is required")
    @Size( message = "Job description must not exceed 500 characters")
    private String jobDescription;

    @NotBlank
    private String fulladdress;

    @NotNull(message = "Start date is required")
    private String startDate;

    @NotNull(message = "End date is required")
    private String endDate;


    private Status status;

    private List<String> responsibilities = new ArrayList<>();

    private List<String> requirements = new ArrayList<>();

    private CompanyDTO company;

    private List<ApplicationDTO> applications;

    private JobCategoryDTO jobCategories;


}



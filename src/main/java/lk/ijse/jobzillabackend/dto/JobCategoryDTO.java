package lk.ijse.jobzillabackend.dto;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.jobzillabackend.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobCategoryDTO {

    private UUID jobCatId;


    @NotBlank(message = "cannot null category name")
    private String jobCatName;

    private List<JobDTO> jobs = new ArrayList<>();
}

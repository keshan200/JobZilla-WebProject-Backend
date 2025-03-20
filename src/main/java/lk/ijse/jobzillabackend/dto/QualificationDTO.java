package lk.ijse.jobzillabackend.dto;


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
    private String qul_name;
    private String university;
    private String year;
    private String description;


}

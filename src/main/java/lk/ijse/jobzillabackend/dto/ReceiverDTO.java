package lk.ijse.jobzillabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class ReceiverDTO {

    private String companyLogo;
    private String companyName;
    private String candidateName;
    private UUID userId;
    private UUID companyId;
    private UUID candidateId;


}

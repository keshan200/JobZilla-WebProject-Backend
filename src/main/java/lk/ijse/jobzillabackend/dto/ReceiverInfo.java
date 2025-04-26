package lk.ijse.jobzillabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverInfo {
    private UUID receiverId;
    private String receiverName;
    private String receiverImg;
}

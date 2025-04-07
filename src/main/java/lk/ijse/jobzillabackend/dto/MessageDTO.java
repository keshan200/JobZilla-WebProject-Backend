package lk.ijse.jobzillabackend.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.jobzillabackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDTO {

    private UUID id;
    private String content;
    private User sender;
    private User receiver;
    private LocalDateTime sentAt;
}
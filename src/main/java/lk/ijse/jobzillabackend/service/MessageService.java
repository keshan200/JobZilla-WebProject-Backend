package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.MessageDTO;
import lk.ijse.jobzillabackend.dto.ReceiverDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    MessageDTO saveMessage(MessageDTO messageDTO);

    List<MessageDTO> getMessagesByReceiverId(UUID receiverId);

    List<MessageDTO> getMessagesBetween(UUID senderId, UUID receiverId);


}

package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.MessageDTO;
import lk.ijse.jobzillabackend.entity.Message;

public interface MessageService {

    MessageDTO saveMessage(MessageDTO messageDTO);
}

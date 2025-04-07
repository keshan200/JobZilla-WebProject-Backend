package lk.ijse.jobzillabackend.controller;

import lk.ijse.jobzillabackend.dto.MessageDTO;
import lk.ijse.jobzillabackend.entity.Message;
import lk.ijse.jobzillabackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/message")
@CrossOrigin(origins = "http://localhost:63342")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat")
    @SendTo("/user/queue/messages")

    public MessageDTO sendMessage(@Payload MessageDTO messageDTO) {
        messageDTO.setSentAt(LocalDateTime.now());
        messageService.saveMessage(messageDTO);
        UUID receiverUid = messageDTO.getReceiver().getUid();
        messagingTemplate.convertAndSendToUser(
                receiverUid.toString(),
                "/queue/messages",
                messageDTO
        );
        return messageDTO;
    }
}

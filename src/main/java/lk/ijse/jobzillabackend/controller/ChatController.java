package lk.ijse.jobzillabackend.controller;

import lk.ijse.jobzillabackend.dto.*;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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

        messageService.saveMessage(messageDTO);
        UUID receiverUid = messageDTO.getReceiver().getUid();
        messagingTemplate.convertAndSendToUser(
                receiverUid.toString(),
                "/queue/messages",
                messageDTO
        );
        return messageDTO;
    }


    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByReceiverId(@PathVariable UUID receiverId) {
        List<MessageDTO> messages = messageService.getMessagesByReceiverId(receiverId);
        return ResponseEntity.ok(messages);
    }


    @GetMapping("/between/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getMessagesBetween(
            @PathVariable UUID senderId,
            @PathVariable UUID receiverId) {
        List<MessageDTO> messages = messageService.getMessagesBetween(senderId, receiverId);

        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messages);
    }


    @GetMapping("/receiver-details/{senderId}")
    public ResponseEntity<List<ReceiverInfo>> getAllDistinctReceivers(@PathVariable UUID senderId) {

        List<ReceiverInfo> receivers = messageService.getAllDistinctReceiversBySenderId(senderId);

        System.out.println(receivers);

        if (receivers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(receivers);
    }
}



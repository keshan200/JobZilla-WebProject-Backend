package lk.ijse.jobzillabackend.service.impl;


import lk.ijse.jobzillabackend.dto.MessageDTO;
import lk.ijse.jobzillabackend.entity.Message;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.MessageRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {



    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageDTO saveMessage(MessageDTO dto) {
        // Map MessageDTO to Message
        Message message = modelMapper.map(dto, Message.class);

        // Fetch sender and receiver
        User sender = userRepository.findById(dto.getSender().getUid())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiver().getUid())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // Set sender and receiver to the message
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSentAt(LocalDateTime.now());

        // Save the message to the database
        Message savedMessage = messageRepository.save(message);

        // Map saved Message entity back to MessageDTO
        return modelMapper.map(savedMessage, MessageDTO.class);
    }
}

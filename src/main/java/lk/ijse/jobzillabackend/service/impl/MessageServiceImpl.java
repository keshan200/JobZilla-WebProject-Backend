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

        Message message = modelMapper.map(dto, Message.class);
        User sender = userRepository.findById(dto.getSender().getUid())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiver().getUid())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        message.setSender(sender);
        message.setReceiver(receiver);


        Message savedMessage = messageRepository.save(message);
        return modelMapper.map(savedMessage, MessageDTO.class);
    }
}

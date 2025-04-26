package lk.ijse.jobzillabackend.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.*;
import lk.ijse.jobzillabackend.entity.Message;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.MessageRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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


    @Override
    @Transactional
    public List<MessageDTO> getMessagesByReceiverId(UUID receiverId) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> messages = messageRepository.findMessagesByReceiverId(receiverId);
        return messages.stream()
                .map(message -> objectMapper.convertValue(message, MessageDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<MessageDTO> getMessagesBetween(UUID senderId, UUID receiverId) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> messages = messageRepository.findMessagesBySenderAndReceiver(senderId, receiverId);
        return messages.stream()
                .map(message -> objectMapper.convertValue(message, MessageDTO.class))
                .collect(Collectors.toList());
    }



   /* @Override
    public List<Object[]> getReceiverProfilesBySenderId(UUID senderId) {
        return messageRepository.findDistinctReceiverDetails(senderId);
    }*/
/*
    public List<ReceiverProfileDTO> getReceiverProfilesBySenderId(UUID senderId) {
        List<Object[]> results = messageRepository.findDistinctReceiverDetails(senderId);
        List<ReceiverProfileDTO> receiverProfiles = new ArrayList<>();

        for (Object[] result : results) {


            CompanyDTO companyDTO = new CompanyDTO();
            CandidateDTO candidateDTO = new CandidateDTO();



            String receiverId = (String) result[0];
            String receiverName = (String) result[1];
            String receiverImg = (String) result[2];


            receiverProfiles.add(new ReceiverProfileDTO(receiverId, receiverName, receiverImg));
        }

        return receiverProfiles;
    }*/


    @Override
    @Transactional
    public List<ReceiverInfo> getAllDistinctReceiversBySenderId(UUID senderId) {

        List<ReceiverInfo> allDistinctReceiversBySenderId = messageRepository.findAllDistinctReceiversBySenderId(senderId);
        System.out.println("sefrchh"+allDistinctReceiversBySenderId);
        return allDistinctReceiversBySenderId;
    }

}





package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.dto.ReceiverDTO;
import lk.ijse.jobzillabackend.entity.Message;
import lk.ijse.jobzillabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {


    @Query("SELECT m FROM Message m WHERE m.receiver.uid = :receiverId")
    List<Message> findMessagesByReceiverId(@Param("receiverId") UUID receiverId);


    @Query("SELECT m FROM Message m WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId")
    List<Message> findMessagesBySenderAndReceiver(@Param("senderId") UUID senderId, @Param("receiverId") UUID receiverId);





}

package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.dto.ReceiverInfo;
import lk.ijse.jobzillabackend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {


    @Query("SELECT m FROM Message m WHERE m.receiver.uid = :receiverId")
    List<Message> findMessagesByReceiverId(@Param("receiverId") UUID receiverId);


    @Query("SELECT m FROM Message m WHERE m.sender.uid = :senderId AND m.receiver.uid = :receiverId")
    List<Message> findMessagesBySenderAndReceiver(@Param("senderId") UUID senderId, @Param("receiverId") UUID receiverId);




    @Query("SELECT new lk.ijse.jobzillabackend.dto.ReceiverInfo(" +
            "m.receiver.uid, " +
            "m.receiver.username, " +
            "COALESCE(ca.img, co.Logo_img, NULL)) " +
            "FROM Message m " +
            "LEFT JOIN m.receiver r " +
            "LEFT JOIN Candidate ca ON r.uid = ca.user.uid " +
            "LEFT JOIN Company co ON r.uid = co.user.uid " +
            "WHERE m.sender.uid = :senderId " +
            "GROUP BY m.receiver.uid, m.receiver.username, ca.img, co.Logo_img " +
            "ORDER BY m.receiver.username")
    List<ReceiverInfo> findAllDistinctReceiversBySenderId(@Param("senderId") UUID senderId);


}

package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}

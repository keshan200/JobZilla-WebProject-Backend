package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QualificationRepository extends JpaRepository<Qualification, UUID> {
}

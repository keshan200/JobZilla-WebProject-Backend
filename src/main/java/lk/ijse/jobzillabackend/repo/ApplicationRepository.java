package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}

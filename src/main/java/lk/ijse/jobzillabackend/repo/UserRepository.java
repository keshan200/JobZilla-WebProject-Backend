package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);
}

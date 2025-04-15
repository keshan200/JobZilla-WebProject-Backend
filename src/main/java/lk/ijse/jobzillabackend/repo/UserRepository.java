package lk.ijse.jobzillabackend.repo;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    User findByEmail(String email);


    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User findByEmailNative(@Param("email") String email);


}

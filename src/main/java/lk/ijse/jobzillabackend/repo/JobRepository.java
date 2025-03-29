package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    @Query(value = "SELECT * FROM job WHERE company_id = (SELECT cid FROM company WHERE user_id = :userId)", nativeQuery = true)
    List<Job> findAllJobsByUserId(@Param("userId") UUID userId);


}

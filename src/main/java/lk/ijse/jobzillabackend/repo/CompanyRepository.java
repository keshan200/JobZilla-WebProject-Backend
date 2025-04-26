package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    @Query("SELECT c FROM Company c WHERE c.user.uid = :userId")
    Optional<Company> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT c FROM Company c WHERE c.cid = :cid")
    Optional<Company> findByCid(@Param("cid") UUID cid);

    /*
    @Query("SELECT COUNT(j) FROM Job j WHERE j.status = 'ACTIVE'")
    long countActiveJobs();*/

    long countByUserStatus(Status user_status);


}

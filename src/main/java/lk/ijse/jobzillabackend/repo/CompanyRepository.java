package lk.ijse.jobzillabackend.repo;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    @Query("SELECT c FROM Company c WHERE c.user.uid = :userId")
    Optional<Company> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT c FROM Company c WHERE c.cid = :cid")
    Optional<Company> findByCid(@Param("cid") UUID cid);






}

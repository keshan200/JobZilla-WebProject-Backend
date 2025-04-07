package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.SocialMedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

    @Query("SELECT c FROM Candidate c WHERE c.cand_id = :candId")
    List<Candidate> findByCandId(@Param("candId") UUID candId);



    Optional<Candidate> findByUser_Uid(UUID uid);

}

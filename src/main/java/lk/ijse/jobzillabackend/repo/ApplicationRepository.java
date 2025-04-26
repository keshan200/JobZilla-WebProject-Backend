package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {


/*
    @Query("SELECT a FROM Application a JOIN a.job j WHERE j.company.cid = :companyId")
    Optional<Application> findApplicationsByCompanyId(@Param("companyId") UUID companyId);

*/
@Query("SELECT a FROM Application a JOIN a.job j WHERE j.company.cid = :companyId")
Optional<Application> findApplicationsByCompanyId(@Param("companyId") UUID companyId);



    @Query("SELECT a FROM Application a " +
            "JOIN FETCH a.job j " +
            "JOIN FETCH j.company c " +
            "WHERE a.candidate.cand_id = :candidateId")
    List<Application> findAppliedJobsByCandidateId(@Param("candidateId") UUID candidateId);


}

package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {


    @Query("SELECT a FROM Application a JOIN a.job j WHERE j.company.id = :companyId")
    Optional<Application> findApplicationsByCompanyId(@Param("companyId") UUID companyId);
}

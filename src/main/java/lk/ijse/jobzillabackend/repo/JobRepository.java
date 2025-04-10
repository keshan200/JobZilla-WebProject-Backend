package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID>{

    @Query("SELECT j FROM Job j WHERE j.company.cid = :companyId")
    List<Job> findAllJobsByCompanyId(@Param("companyId") UUID companyId);


    @Query("SELECT j FROM Job j WHERE j.jobId = :jId")
    List<Job> findAllJobsByJobId(@Param("jId") UUID jId);


    @Query("SELECT j FROM Job j WHERE (:jobTitle IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :jobTitle, '%'))) AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND (:jobType IS NULL OR LOWER(j.jobType) LIKE LOWER(CONCAT('%', :jobType, '%')))")
    List<Job> searchJobs(@Param("jobTitle") String jobTitle, @Param("location") String location, @Param("jobType") String jobType);




}
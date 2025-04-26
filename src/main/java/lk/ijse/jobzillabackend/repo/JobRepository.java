package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Application;
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


    @Query("SELECT j FROM Job j WHERE " +
            "(:country IS NULL OR LOWER(j.country) = LOWER(:country)) AND " +
            "(:jobTitle IS NULL OR LOWER(j.jobTitle) = LOWER(:jobTitle)) AND " +
            "(:jobType IS NULL OR LOWER(j.jobType) = LOWER(:jobType))")
    List<Job> searchJobs(
            @Param("country") String country,
            @Param("jobTitle") String jobTitle,
            @Param("jobType") String jobType
    );


    @Query("SELECT COUNT(j) FROM Job j WHERE j.company.cid = :companyId AND j.status = 'ACTIVE'")
    int countActiveJobsByCompanyId(@Param("companyId") UUID companyId);



    @Query("SELECT j FROM Job j WHERE " +
            "(:category IS NULL OR LOWER( j.jobCategory) LIKE CONCAT('%', LOWER(:category), '%')) AND" +
            "(:keyword IS NULL OR LOWER(j.jobTitle) LIKE CONCAT('%', LOWER(:keyword), '%')) AND " +
            "(:location IS NULL OR LOWER(j.location) LIKE CONCAT('%', LOWER(:location), '%')) AND " +
            "(:type IS NULL OR j.jobType IN :type)")
    List<Job> findJobsByJobsPage(
            @Param("category") String category,
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("type") List<String> type
    );


    @Query("SELECT COUNT(j) FROM Job j WHERE j.status = 'ACTIVE'")
    long countActiveJobs();

}
package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobCategoryRepository extends JpaRepository<JobCategory, UUID> {

    boolean existsByJobCatName(String jobCatName);

}

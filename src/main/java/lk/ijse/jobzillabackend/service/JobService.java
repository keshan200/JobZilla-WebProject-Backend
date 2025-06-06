package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.entity.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface JobService {

    int saveJob(JobDTO jobDTO);
    int updateJob(JobDTO jobDTO);
    int deleteJob(int id);
    List<JobDTO> getAllJobs();

    List<JobDTO> getJobsByUserId(UUID companyId);

    List<JobDTO> getJobsByJobId(UUID jobId);

    List<JobDTO> searchJobs(String country, String jobTitle, String jobType);

    int getActiveJobCountByCompanyId(UUID companyId);

    List<JobDTO> searchJobs(String category, String keyword, String location, List<String> type);


    long activejobCount();
}

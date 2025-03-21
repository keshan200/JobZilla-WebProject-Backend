package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.JobDTO;

import java.util.List;

public interface JobService {

    int saveJob(JobDTO jobDTO);
    int updateJob(JobDTO jobDTO);
    int deleteJob(int id);
    List<JobDTO> getAllJobs();
}

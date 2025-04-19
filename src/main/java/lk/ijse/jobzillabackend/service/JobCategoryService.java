package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.JobCategoryDTO;
import lk.ijse.jobzillabackend.entity.JobCategory;

import java.util.List;

public interface JobCategoryService {

    int saveJobCategory(JobCategoryDTO jobCategoryDTO);
    int updateJobCategory(JobCategoryDTO jobCategoryDTO);
    List<JobCategoryDTO> getAllJobCategory();
    int deleteJobCategory(int id);
}

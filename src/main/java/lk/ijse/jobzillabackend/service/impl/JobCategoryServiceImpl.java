package lk.ijse.jobzillabackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.jobzillabackend.dto.JobCategoryDTO;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.entity.JobCategory;
import lk.ijse.jobzillabackend.repo.JobCategoryRepository;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.service.JobCategoryService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobCategoryServiceImpl implements JobCategoryService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;


    @Override
    public int saveJobCategory(JobCategoryDTO jobCategoryDTO) {
        try {

            if (jobCategoryDTO.getJobCatId() != null && jobCategoryRepository.existsById(jobCategoryDTO.getJobCatId())) {
                return VarList.Not_Acceptable;
            } else {

                if (jobCategoryRepository.existsByJobCatName(jobCategoryDTO.getJobCatName().trim())) {
                    return VarList.Not_Acceptable;
                }

                jobCategoryRepository.save(modelMapper.map(jobCategoryDTO, JobCategory.class));
                return VarList.Created;
            }
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public int updateJobCategory(JobCategoryDTO jobCategoryDTO) {
        return 0;
    }

    @Override
    public List<JobCategoryDTO> getAllJobCategory() {
        return List.of();
    }

    @Override
    public int deleteJobCategory(int id) {
        return 0;
    }
}

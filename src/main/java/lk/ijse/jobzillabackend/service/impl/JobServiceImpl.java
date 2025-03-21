package lk.ijse.jobzillabackend.service.impl;

import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.service.JobService;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public int saveJob(JobDTO jobDTO) {
        try {
            if (jobDTO.getJobId() != null && jobRepository.existsById(jobDTO.getJobId())) {
                return VarList.Not_Acceptable;
            } else {
                jobRepository.save(modelMapper.map(jobDTO, Job.class));
                return VarList.Created;
            }
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public int updateJob(JobDTO jobDTO) {
        try {
            if (jobDTO.getJobId() != null && jobRepository.existsById(jobDTO.getJobId())) {
                return VarList.Not_Acceptable;
            } else {
                jobRepository.save(modelMapper.map(jobDTO, Job.class));
                return VarList.Created;
            }
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }

    @Override
    public int deleteJob(int id) {
        return 0;
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return modelMapper.map(jobRepository.findAll(),new TypeToken<List<JobDTO>>(){}.getType());
    }
}

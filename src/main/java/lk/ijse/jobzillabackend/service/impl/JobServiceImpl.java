package lk.ijse.jobzillabackend.service.impl;

import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.service.JobService;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;


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


    @Override
    public List<JobDTO> getJobsByUserId(UUID userId) {
        List<Job> jobs = jobRepository.findAllJobsByUserId(userId);
        if (jobs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "මෙම පරිශීලක අංකය සඳහා රැකියා නොමැත");
        }
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }


}

package lk.ijse.jobzillabackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.sepecifications.JobSpecifications;
import lk.ijse.jobzillabackend.service.JobService;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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



   /* @Override
    @Transactional
    public List<JobDTO> getAllJobs() {
       *//* return modelMapper.map(jobRepository.findAll(),new TypeToken<List<JobDTO>>(){}.getType());*//*
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public List<JobDTO> getJobsByUserId(UUID companyId) {
        List<Job> jobs = jobRepository.findAllJobsByCompanyId(companyId);
        ModelMapper customMapper = new ModelMapper();
        customMapper.typeMap(Job.class, JobDTO.class)
                .addMappings(mapper -> mapper.skip(JobDTO::setCompany));

        return jobs.stream()
                .map(job -> customMapper.map(job, JobDTO.class))
                .toList();
    }



    @Override
    @Transactional
    public List<JobDTO> getJobsByJobId(UUID jobId) {
        List<Job> jobs = jobRepository.findAllJobsByJobId(jobId);
        ModelMapper modelMapper = new ModelMapper();
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .toList();
    }
*/

    @Override
    @Transactional
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();

        return jobs.stream()
                .map(job -> {
                    JobDTO jobDTO = objectMapper.convertValue(job, JobDTO.class);
                    if (job.getCompany() != null) {
                        jobDTO.setCompany(objectMapper.convertValue(job.getCompany(), CompanyDTO.class));
                    }
                    return jobDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<JobDTO> getJobsByUserId(UUID companyId) {
        List<Job> jobs = jobRepository.findAllJobsByCompanyId(companyId);
        ObjectMapper objectMapper = new ObjectMapper();
        return jobs.stream()
                .map(job -> {
                    JobDTO jobDTO = objectMapper.convertValue(job, JobDTO.class);
                    if (job.getCompany() != null) {
                        jobDTO.setCompany(objectMapper.convertValue(job.getCompany(), CompanyDTO.class));
                    }
                    return jobDTO;
                })
                .toList();
    }

    @Override
    @Transactional
    public List<JobDTO> getJobsByJobId(UUID jobId) {
        List<Job> jobs = jobRepository.findAllJobsByJobId(jobId);
        ObjectMapper objectMapper = new ObjectMapper();

        return jobs.stream()
                .map(job -> {
                    JobDTO jobDTO = objectMapper.convertValue(job, JobDTO.class);
                    CompanyDTO companyDTO = objectMapper.convertValue(job.getCompany(), CompanyDTO.class);
                    jobDTO.setCompany(companyDTO);

                    return jobDTO;
                })
                .toList();
    }




    @Override
    @Transactional
    public List<JobDTO> searchJobs(String jobTitle, String location, String jobType) {


        ObjectMapper objectMapper = new ObjectMapper();

        List<Job> jobs = jobRepository.searchJobs(
                (jobTitle != null && !jobTitle.isEmpty()) ? "%" + jobTitle + "%" : null,
                (location != null && !location.isEmpty()) ? "%" + location + "%" : null,
                (jobType != null && !jobType.isEmpty()) ? "%" + jobType + "%" : null
        );

        return jobs.stream()
                .map(job -> objectMapper.convertValue(job, JobDTO.class))
                .collect(Collectors.toList());
    }
}

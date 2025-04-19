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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);



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
    public List<JobDTO> searchJobs(String country, String jobTitle, String jobType) {
        logger.info("Searching for jobs with country: {}, jobTitle: {}, jobType: {}", country, jobTitle, jobType);
        ObjectMapper objectMapper = new ObjectMapper();
        return jobRepository.searchJobs(country, jobTitle, jobType)
                .stream()
                .map(job -> objectMapper.convertValue(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getActiveJobCountByCompanyId(UUID companyId) {
        try {
            int count = jobRepository.countActiveJobsByCompanyId(companyId);
            System.out.println("Active jobs count: " + count);
            return count;
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }



  /*  @Override
    public int getActiveJobCountByCompanyId(UUID companyId) {
        try {
            int count = jobRepository.countActiveJobsByCompanyId(companyId);
            System.out.println("Active jobs count: " + count);
            return count;
        } catch (Exception e) {
            return VarList.Internal_Server_Error;
        }
    }*/




    @Override
    @Transactional
    public List<JobDTO> searchJobs(String category, String keyword, String location, List<String> type) {

        ObjectMapper objectMapper = new ObjectMapper();

        if (type != null && type.isEmpty()) {
            type = null;
        }


        List<Job> jobs = jobRepository.findJobsByJobsPage(category, keyword, location, type);
        System.out.println("search2"+jobs);

        return jobs.stream()
                .map(job -> objectMapper.convertValue(job, JobDTO.class))
                .collect(Collectors.toList());
    }

}

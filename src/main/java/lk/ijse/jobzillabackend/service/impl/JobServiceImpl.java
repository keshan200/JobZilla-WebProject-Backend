package lk.ijse.jobzillabackend.service.impl;

import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.service.JobService;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public int saveJob(JobDTO jobDTO) {
        if (jobDTO.getJobId() == null) {
            return VarList.Bad_Request;
        }
        if (jobRepository.existsById(jobDTO.getJobId())){
            return VarList.Not_Acceptable;
        }else {
            jobRepository.save(modelMapper.map(jobDTO, Job.class));
            return VarList.Created;
        }
    }
}

package lk.ijse.jobzillabackend.service.impl;


import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.service.JobPostService;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class PostJobServiceImpl implements JobPostService {



    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public int postJob(UUID companyId, UUID jobId) {
        Company company = companyRepository.findById(companyId)
                .orElse(null);
        if (company == null) {
            return VarList.Not_Found;
        }

        Job job = jobRepository.findById(jobId)
                .orElse(null);
        if (job == null) {
            return VarList.Not_Found;
        }

        if (company.getJobs().contains(job)) {
            return VarList.Conflict;
        }

        company.getJobs().add(job);
        job.getCompanies().add(company);

        companyRepository.save(company);
        jobRepository.save(job);
        return VarList.Created;
    }
}

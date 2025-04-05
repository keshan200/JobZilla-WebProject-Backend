package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.ApplicationDTO;
import lk.ijse.jobzillabackend.entity.Application;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.enums.ApplicationStatus;
import lk.ijse.jobzillabackend.repo.ApplicationRepository;
import lk.ijse.jobzillabackend.repo.CandidateRepository;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.service.ApplicationService;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public int saveApplication(ApplicationDTO applicationDTO, MultipartFile file) throws IOException {

        if (applicationDTO.getId() != null && applicationRepository.existsById(applicationDTO.getId())) {
            return VarList.Not_Acceptable;
        }

        candidateRepository.findById(applicationDTO.getCandidate().getCand_id())
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));

        Application application = modelMapper.map(applicationDTO, Application.class);

        System.out.println("???????????0"+application);

        if (file != null && !file.isEmpty() && file.getOriginalFilename().endsWith(".pdf")) {
            String fileName = applicationDTO.getEmail() + "_" + System.currentTimeMillis() + ".pdf";
            String uploadDir = "uploads/resumes/";
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            application.setResume(uploadDir + fileName);
        }


        applicationRepository.save(application);
        return VarList.Created;
    }




    @Override
    @Transactional
    public List<ApplicationDTO> getApplications() {

        List<Application> applications = applicationRepository.findAll();
        List<ApplicationDTO> applicationDTOS = applications.stream().map(application -> {
            ApplicationDTO dto = modelMapper.map(application, ApplicationDTO.class);


            if (application.getCandidate() != null) {
                dto.setCandidate(modelMapper.map(application.getCandidate(), Candidate.class));
            }

            if (application.getJob() != null) {
                dto.setJob(modelMapper.map(application.getJob(), Job.class));
            }

            return dto;
        }).collect(Collectors.toList());

        return applicationDTOS;
    }
}

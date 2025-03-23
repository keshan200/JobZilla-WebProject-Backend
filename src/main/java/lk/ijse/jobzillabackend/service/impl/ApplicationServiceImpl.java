package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.ApplicationDTO;
import lk.ijse.jobzillabackend.entity.Application;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.enums.ApplicationStatus;
import lk.ijse.jobzillabackend.repo.ApplicationRepository;
import lk.ijse.jobzillabackend.repo.CandidateRepository;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.service.ApplicationService;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public int saveApplication(ApplicationDTO applicationDTO, MultipartFile file) {

        if (applicationDTO.getId() != null && applicationRepository.existsById(applicationDTO.getId())) {
            return VarList.Not_Acceptable;
        }

        Application application = modelMapper.map(applicationDTO, Application.class);

        if (application.getStatus() == null) {
            application.setStatus(ApplicationStatus.PENDING);
        }

        if (file != null && !file.isEmpty() && file.getOriginalFilename().endsWith(".pdf")) {
            try {
                String fileName = applicationDTO.getEmail() + "_" + System.currentTimeMillis() + ".pdf";
                String uploadDir = "uploads/resumes/";
                FileUploadUtil.saveFile(uploadDir, fileName, file);
                application.setResume(uploadDir + fileName);
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        } else if (file != null && !file.isEmpty()) {
            return VarList.Unsupported_Media_Type;
        }

        applicationRepository.save(application);
        return VarList.Created;
    }



    @Override
    public List<ApplicationDTO> getApplications() {
        return List.of();
    }
}

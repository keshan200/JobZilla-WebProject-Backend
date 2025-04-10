package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.ApplicationDTO;
import lk.ijse.jobzillabackend.entity.Application;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    int saveApplication(ApplicationDTO applicationDTO, MultipartFile file) throws IOException;
    List<ApplicationDTO> getApplications();

    List<ApplicationDTO> getApplicationsByCompanyId(UUID companyId);
}

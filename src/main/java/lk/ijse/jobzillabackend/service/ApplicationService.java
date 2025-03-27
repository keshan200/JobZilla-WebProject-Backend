package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.ApplicationDTO;
import lk.ijse.jobzillabackend.entity.Application;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApplicationService {

    int saveApplication(ApplicationDTO applicationDTO, MultipartFile file) throws IOException;
    List<ApplicationDTO> getApplications();
}

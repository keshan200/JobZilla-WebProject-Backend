package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.entity.Company;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    int saveCompany(CompanyDTO companyDto, List<MultipartFile> files);
    int updateCompany(CompanyDTO companyDto, List<MultipartFile> files);
    List<CompanyDTO> getAllCompanies();
    List<CompanyDTO>getCompanyByUserID(UUID userId);
}

package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    int saveCompany(CompanyDTO companyDto, List<MultipartFile> files);
    int updateCompany(CompanyDTO companyDto, List<MultipartFile> files);
    List<CompanyDTO> getAllCompanies();


}

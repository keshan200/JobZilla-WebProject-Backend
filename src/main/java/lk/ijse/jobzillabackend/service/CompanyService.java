package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.entity.Company;

import java.util.List;

public interface CompanyService {
    int saveCompany(CompanyDTO companyDto);
    int updateCompany(CompanyDTO companyDto);
    List<CompanyDTO> getAllCompanies();


}

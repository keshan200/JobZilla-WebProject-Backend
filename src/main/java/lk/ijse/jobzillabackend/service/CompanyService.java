package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.entity.Company;

public interface CompanyService {
    int saveCompany(CompanyDTO companyDto);
    Company updateCompany(CompanyDTO companyDto);

}

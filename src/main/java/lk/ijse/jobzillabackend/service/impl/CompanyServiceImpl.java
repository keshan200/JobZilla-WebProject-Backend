package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CompanyService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public int saveCompany(CompanyDTO companyDto) {
        if (companyDto.getCid() != null && companyRepository.existsById(companyDto.getCid())) {
            return VarList.Not_Acceptable;
        }
        User user = userRepository.findById(companyDto.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("user"+user);

        Company company = modelMapper.map(companyDto, Company.class);
        companyRepository.save(company);
        return VarList.Created;
    }

    @Override
    public int updateCompany(CompanyDTO companyDto) {
        if (companyDto.getCid() == null || !companyRepository.existsById(companyDto.getCid())) {
            return VarList.Not_Found;
        }

        userRepository.findById(companyDto.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User Account not found"));

        Company existingCompany = companyRepository.findById(companyDto.getCid())
                .orElseThrow(() -> new RuntimeException("company not found"));

        modelMapper.map(companyDto, existingCompany);

        companyRepository.save(existingCompany);
        return VarList.Created;

    }

    @Override
    @Transactional
    public List<CompanyDTO> getAllCompanies() {
        return modelMapper.map(companyRepository.findAll(),new TypeToken<List<CompanyDTO>>(){}.getType());
    }
}

package lk.ijse.jobzillabackend.service.impl;

import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CompanyService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Company updateCompany(CompanyDTO companyDto) {
        Company existingCompany = companyRepository.findById(companyDto.getCid())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        existingCompany.setLogo_img(companyDto.getLogo_img());
        existingCompany.setBackground_img(companyDto.getBackground_img());
        existingCompany.setCity(companyDto.getCity());
        existingCompany.setCompany_name(companyDto.getCompany_name());
        existingCompany.setCountry(companyDto.getCountry());
        existingCompany.setDescription(companyDto.getDescription());
        existingCompany.setEst_since(companyDto.getEst_since());
        existingCompany.setFull_address(companyDto.getFull_address());


        System.out.println(companyDto.getCid());
        return companyRepository.save(existingCompany);

    }
}

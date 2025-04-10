package lk.ijse.jobzillabackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.dto.SocialMediaDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.JobRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CompanyService;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public int saveCompany(CompanyDTO companyDto, List<MultipartFile> files) {
        if (companyDto.getCid() != null && companyRepository.existsById(companyDto.getCid())) {
            return VarList.Not_Acceptable;
        }

        userRepository.findById(companyDto.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Company company = modelMapper.map(companyDto, Company.class);

        if (files != null && !files.isEmpty()) {
            List<String> imagePaths = new ArrayList<>();

            try {
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    if (file != null && !file.isEmpty()) {
                        String fileName = companyDto.getUser().getUid() + "_" + file.getOriginalFilename();
                        String uploadDir = "company/";
                        FileUploadUtil.saveFile(uploadDir, fileName, file);
                        String filePath = uploadDir  + fileName;
                        imagePaths.add(filePath);

                        if (i == 0) {
                            company.setLogo_img(filePath);
                        } else if (i == 1) {
                            company.setBackground_img(filePath);
                        }
                    }
                }

                if (imagePaths.size() > 2) {
                    company.setImage_collection(imagePaths.subList(2, imagePaths.size()));
                }
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }

        Company save = companyRepository.save(company);

        return VarList.Created;
    }




    @Transactional
    @Override
    public int updateCompany(CompanyDTO companyDto, List<MultipartFile> files) {

        if (companyDto.getCid() == null || !companyRepository.existsById(companyDto.getCid())) {
            return VarList.Not_Found;
        }

        if (companyDto.getImage_collection() == null) {
            companyDto.setImage_collection(new ArrayList<>());
        }
        if (companyDto.getSocialMediaProfiles() == null) {
            companyDto.setSocialMediaProfiles(new ArrayList<>());
        }

        userRepository.findById(companyDto.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User Account not found"));

        Company existingCompany = companyRepository.findById(companyDto.getCid())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        existingCompany.getImage_collection().clear();
        existingCompany.getSocialMediaProfiles().clear();

        modelMapper.map(companyDto, existingCompany);

        if (files != null && !files.isEmpty()) {
            List<String> updatedImagePaths = new ArrayList<>();

            try {
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    if (file != null && !file.isEmpty()) {
                        String fileName = companyDto.getUser().getUid() + "_" + file.getOriginalFilename();
                        String uploadDir = "company/" + companyDto.getUser().getUid();
                        FileUploadUtil.saveFile(uploadDir, fileName, file);
                        String filePath = "uploads/" + uploadDir + "/" + fileName;
                        updatedImagePaths.add(filePath);

                        if (i == 0) {
                            existingCompany.setLogo_img(filePath);
                        } else if (i == 1) {
                            existingCompany.setBackground_img(filePath);
                        }
                    }
                }

                if (updatedImagePaths.size() > 2) {
                    existingCompany.setImage_collection(updatedImagePaths.subList(2, updatedImagePaths.size()));
                }
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }

        companyRepository.save(existingCompany);
        return VarList.Created;
    }


    @Override
    @Transactional
    public List<CompanyDTO> getAllCompanies() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(company -> {
            CompanyDTO dto = objectMapper.convertValue(company, CompanyDTO.class);
            dto.setLogo_img("http://localhost:8080/uploads/" + company.getLogo_img());
            System.out.println("Image URL: " + dto.getLogo_img());
            return dto;
        }).collect(Collectors.toList());

    }


    @Override
    @Transactional
    public List<CompanyDTO> getCompanyByUserID(UUID userId) {
        Optional<Company> companyOpt = companyRepository.findByUserId(userId);
        if (companyOpt.isEmpty()) {
            return List.of();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return companyOpt.stream()
                .map(company -> objectMapper.convertValue(company, CompanyDTO.class))
                .toList();
    }



    @Override
    @Transactional
    public List<CompanyDTO> getCompaniesByCid(UUID cid) {
        Optional<Company> companyOpt = companyRepository.findByCid(cid);
        if (companyOpt.isEmpty()) {
            return List.of();
        }
        Company company = companyOpt.get();
        ObjectMapper objectMapper = new ObjectMapper();
        CompanyDTO companyDTO = objectMapper.convertValue(company, CompanyDTO.class);
        companyDTO.setJobs(null);
        return List.of(companyDTO);
    }

    @Override
    public UUID getCompanyID(UUID id) {
        Company company = companyRepository.findByCid(id).orElseThrow(() -> new RuntimeException("Company not found"));
        return company.getCid();
    }


}



   /* @Override
    @Transactional
    public List<CompanyDTO> getCompaniesByCid(UUID cid) {
        Optional<Company> companies = companyRepository.findByCid(cid);

        System.out.println("coma"+companies);
        return companies
                .map(company -> {
                    if (company.getUser() == null) {
                        throw new IllegalStateException("User is null for company " + company.getCid());
                    }
                    return Collections.singletonList(modelMapper.map(company, CompanyDTO.class));
                })
                .orElseGet(Collections::emptyList);
    }
*/
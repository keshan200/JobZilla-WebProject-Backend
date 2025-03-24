package lk.ijse.jobzillabackend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CompanyService;
import lk.ijse.jobzillabackend.service.UserService;
import lk.ijse.jobzillabackend.util.JwtUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

    private final CompanyService companyService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public CompanyController(CompanyService companyService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.companyService = companyService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public ResponseEntity<ResponseDTO> registerCompany(
            @RequestPart("company") @Valid String companyData,
            @RequestPart("files") List<MultipartFile> files) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CompanyDTO companyDTO = objectMapper.readValue(companyData, CompanyDTO.class);

            int result = companyService.saveCompany(companyDTO, files);


            switch (result) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Company created successfully", companyDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Company already exists", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(VarList.Bad_Request, "Error saving company", null));
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Internal server error", e.getMessage()));
        }
    }

   /* @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('EMPLOYER','ADMIN')")
    public ResponseEntity<ResponseDTO> updateCompany(
            @RequestPart("company") @Valid String companyData,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CompanyDTO companyDTO = objectMapper.readValue(companyData, CompanyDTO.class);

            int result = companyService.updateCompany(companyDTO, files);

            return switch (result) {
                case VarList.Created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Company updated successfully", companyDTO));

                case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Company not found!", companyDTO));

                case VarList.Not_Acceptable -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Company URL already exists!", companyDTO));

                default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "An unknown error occurred", companyDTO));
            };

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error: " , ex.getMessage()));
        }
    }

*/



    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('EMPLOYER','ADMIN')")
    public ResponseEntity<ResponseDTO> updateCompany(
            @RequestPart("company") @Valid String companyData,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CompanyDTO companyDTO;

            try {
                companyDTO = objectMapper.readValue(companyData, CompanyDTO.class);
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Invalid JSON structure", e.getMessage()));
            }

            int result = companyService.updateCompany(companyDTO, files);

            return switch (result) {
                case VarList.Created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Company updated successfully", companyDTO));

                case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Company not found!", companyDTO));

                case VarList.Not_Acceptable -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Company URL already exists!", companyDTO));

                default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "An unknown error occurred", companyDTO));
            };

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error: ", ex.getMessage()));
        }
    }


    @GetMapping(value = "getAll")
    @PreAuthorize("hasAnyAuthority('EMPLOYER','ADMIN')")
    public List<CompanyDTO> getAllUsers() {

        try {
            return companyService.getAllCompanies();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}



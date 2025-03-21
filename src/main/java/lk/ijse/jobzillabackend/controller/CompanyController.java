package lk.ijse.jobzillabackend.controller;


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

import java.util.List;

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
    public ResponseEntity<ResponseDTO> registerCompany(@RequestBody @Valid CompanyDTO companyDTO) {

        User user = userRepository.findById(companyDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(user);
        try{
            int res = companyService.saveCompany(companyDTO);

            switch (res){
                case VarList.Created -> {
                       return ResponseEntity.status(HttpStatus.CREATED)
                               .body(new ResponseDTO(VarList.Created,"Success",companyDTO));

                }
                     default -> throw new IllegalStateException("Unexpected value: " + res);
            }
        } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                   .body(new ResponseDTO(VarList.Bad_Gateway,"Error",e.getMessage()));
        }


    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('EMPLOYER','ADMIN')")
    public  ResponseEntity<ResponseDTO> updateCompany(@RequestBody @Valid CompanyDTO companyDTO) {

        try{

            int res = companyService.updateCompany(companyDTO);

            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "success", companyDTO));
                }

                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "url already added!!!", companyDTO));
                }

                case VarList.Not_Found -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "url not found!!!", companyDTO));
                }

                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", companyDTO));
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",companyDTO));
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



package lk.ijse.jobzillabackend.controller;


import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.service.CompanyService;
import lk.ijse.jobzillabackend.util.JwtUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

    private final CompanyService companyService;
    private final JwtUtil jwtUtil;

    public CompanyController(CompanyService companyService, JwtUtil jwtUtil) {
        this.companyService = companyService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerCompany(@RequestBody @Valid CompanyDTO companyDTO) {

        try{
            int res = companyService.saveCompany(companyDTO);

            switch (res){
                case VarList.Created -> {
                       return ResponseEntity.status(HttpStatus.CREATED)
                               .body(new ResponseDTO(VarList.Created,"Sucsess",companyDTO));

                }
                     default -> throw new IllegalStateException("Unexpected value: " + res);
            }
        } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                   .body(new ResponseDTO(VarList.Bad_Gateway,"Error",e.getMessage()));
        }


    }


    @PutMapping("/update")
    public Company updateCompany(@RequestBody @Valid CompanyDTO companyDTO) {

        Company res = companyService.updateCompany(companyDTO);


        System.out.println(companyDTO.getCid());

        return res;
    }

}

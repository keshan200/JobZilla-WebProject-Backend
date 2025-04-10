package lk.ijse.jobzillabackend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.ApplicationDTO;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.service.ApplicationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('CANDIDATE')")
    public ResponseEntity<ResponseDTO> saveApplication(
            @RequestPart("application") @Valid String applicationData,
            @RequestPart("file") MultipartFile file) {


        try {

            ObjectMapper objectMapper = new ObjectMapper();
            ApplicationDTO applicationDTO = objectMapper.readValue(applicationData, ApplicationDTO.class);

            int responseCode = applicationService.saveApplication(applicationDTO, file);

             switch (responseCode) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "application created successfully", applicationDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "application already exists", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(VarList.Bad_Request, "Error saving application", applicationDTO));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Internal server error", e.getMessage()));
        }
    }





    @GetMapping("/getAll")
    /*@PreAuthorize("hasAnyAuthority('EMPLOYER')")*/
    public List<ApplicationDTO> getAllApplications() {
        try {
            return applicationService.getApplications();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching applications: " + e.getMessage());
        }
    }



    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByCompanyId(@PathVariable UUID companyId) {
        List<ApplicationDTO> applicationDTOs = applicationService.getApplicationsByCompanyId(companyId);
        return ResponseEntity.ok(applicationDTOs);
    }

}

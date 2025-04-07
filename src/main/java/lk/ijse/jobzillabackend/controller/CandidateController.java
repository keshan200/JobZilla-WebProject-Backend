package lk.ijse.jobzillabackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.*;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.service.CandidateService;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/candidate")
@CrossOrigin(origins = "http://localhost:63342")
public class CandidateController {

   private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping(value = "/register" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyAuthority('CANDIDATE')")
    public ResponseEntity<ResponseDTO> saveCandidate(
            @RequestPart("candidate") @Valid String candidateData,
            @RequestPart("file") MultipartFile file) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CandidateDTO candidateDTO = objectMapper.readValue(candidateData, CandidateDTO.class);

            int result = candidateService.saveCandidate(candidateDTO, file);

            switch (result) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Candidate created successfully", candidateDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Candidate already exists", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(VarList.Bad_Request, "Error saving candidate", null));
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Internal server error", e.getMessage()));
        }
    }



    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('CANDIDATE','ADMIN')")
    public ResponseEntity<ResponseDTO>updateCandidate(
            @RequestPart("candidate") @Valid String candidateDTO,
            @RequestPart(value = "file", required = false) MultipartFile file){
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                CandidateDTO candidateDTO2 = objectMapper.readValue(candidateDTO, CandidateDTO.class);
                int res = candidateService.updateCandidate(candidateDTO2, file);

                System.out.println("Parsed CandidateDTO: " + candidateDTO2);
                switch (res) {
                    case VarList.Created -> {
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseDTO(VarList.Created, "Candidate updated successfully", candidateDTO));
                    }

                    case VarList.Not_Found -> {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseDTO(VarList.Not_Found, "Candidate not found", candidateDTO));
                    }

                    default -> {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ResponseDTO(VarList.Bad_Request, "Update failed", candidateDTO));
                    }
                }

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error", e.getMessage()));
            }

    }



    @GetMapping(value = "getAll")
    public List<CandidateDTO> getAllUsers() {
        try {
            return candidateService.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    @GetMapping("/getCandidateByCandId/{candId}")
    public List<CandidateDTO> getCandidatesByCandId(@PathVariable UUID candId) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+candId);
        return candidateService.getCandidatesByCandId(candId);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CandidateDTO>> getCompanyByUserID(@PathVariable UUID userId) {
        List<CandidateDTO> candidateDTOS = candidateService.getCandidateByUserId(userId);
        if (candidateDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(candidateDTOS);
    }


}



package lk.ijse.jobzillabackend.controller;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.QualificationDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/qualification")
public class QualificationController {


    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }


    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('CANDIDATE')")
    public ResponseEntity<ResponseDTO> addQualifications(@RequestBody @Valid QualificationDTO qualificationDTO) {

        try {
            int response = qualificationService.saveQualification(qualificationDTO);

            switch (response){
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created,"Success",qualificationDTO));

                }
                case VarList.Conflict -> {
                    return ResponseEntity.status(VarList.Conflict)
                            .body(new ResponseDTO(VarList.Conflict, "Duplicate qualification entry", null));
                }
                case VarList.Bad_Request -> {
                    return ResponseEntity.status(VarList.Bad_Request)
                            .body(new ResponseDTO(VarList.Bad_Request, "Invalid input data", qualificationDTO));
                }
                default -> {
                    return ResponseEntity.status(VarList.Internal_Server_Error)
                            .body(new ResponseDTO(VarList.Internal_Server_Error, "Unexpected error occurred", null));
                }

            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",qualificationDTO));
        }

    }

}

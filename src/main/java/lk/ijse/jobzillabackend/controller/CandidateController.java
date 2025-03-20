package lk.ijse.jobzillabackend.controller;

import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.dto.SocialMediaDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.service.CandidateService;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/candidate")
public class CandidateController {

   private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('CANDIDATE')")
    public ResponseEntity<ResponseDTO> saveCandidate(@RequestBody @Valid CandidateDTO candidateDTO) {
        try{
            int res = candidateService.saveCandidate(candidateDTO);
            System.out.println("sid"+candidateDTO.getCand_id());
            switch (res){
                case VarList.Created ->{
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created,"success",candidateDTO));
                }

                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable,"url already added!!!",candidateDTO));
                }

                case VarList.Not_Found -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found,"url not found!!!",candidateDTO));
                }

                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway,"Error",candidateDTO));
                }

            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",candidateDTO));
        }
    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('CANDIDATE','ADMIN')")
    public ResponseEntity<ResponseDTO>updateCandidate(@RequestBody @Valid CandidateDTO candidateDTO){

        try{

            int res = candidateService.updateCandidate(candidateDTO);

            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "success", candidateDTO));
                }

                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "url already added!!!", candidateDTO));
                }

                case VarList.Not_Found -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found, "url not found!!!", candidateDTO));
                }

                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", candidateDTO));
                }
             }

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",candidateDTO));
        }
    }


    @GetMapping(value = "getAll")
    @PreAuthorize("hasAnyAuthority('CANDIDATE','ADMIN')")
    public List<CandidateDTO> getAllUsers() {

        try {
            return candidateService.getAll();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}

package lk.ijse.jobzillabackend.controller;

import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.service.JobPostService;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/jobs")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;


    @PostMapping("/post-job")
    public ResponseEntity<ResponseDTO> postJob(
            @RequestParam UUID companyId,
            @RequestParam UUID jobId){

        try {
            int result = jobPostService.postJob(companyId, jobId);

            return switch (result) {
                case VarList.Created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Job successfully linked to the company", null));

                case VarList.Conflict -> ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ResponseDTO(VarList.Conflict, "Job is already linked to the company", null));

                case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "Company or Job not found", null));

                default -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Invalid request", null));
            };

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error: ", e.getMessage()));
        }
    }
}
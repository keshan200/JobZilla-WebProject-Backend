package lk.ijse.jobzillabackend.controller;


import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.JobCategoryDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.service.JobCategoryService;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/jobCategory")
@CrossOrigin(origins = "http://localhost:63342")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }



    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveJobCategory(@RequestBody @Valid JobCategoryDTO jobCategoryDTO) {

        try{

            int saveJobCat = jobCategoryService.saveJobCategory(jobCategoryDTO);

            switch (saveJobCat) {
                case VarList.Created -> {

                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Category created successfully", jobCategoryDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Category already exists", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(VarList.Bad_Request, "Error saving Category", null));
                }
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Internal server error", e.getMessage()));
        }



    }
}

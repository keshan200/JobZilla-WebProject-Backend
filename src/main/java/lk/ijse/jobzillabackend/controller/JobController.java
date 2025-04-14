package lk.ijse.jobzillabackend.controller;



import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.email.EmailService;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.service.JobService;
import lk.ijse.jobzillabackend.service.impl.JobServiceImpl;
import lk.ijse.jobzillabackend.util.VarList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/job")
@CrossOrigin(origins = "http://localhost:63342")

public class JobController {

    private final JobService jobService;
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private final JobServiceImpl jobServiceImpl;
    private final EmailService emailService;

    public JobController(JobService jobService, JobServiceImpl jobServiceImpl, EmailService emailService) {
        this.jobService = jobService;
        this.jobServiceImpl = jobServiceImpl;
        this.emailService = emailService;
    }



    @PostMapping("/save")
    public ResponseEntity<ResponseDTO>saveJob(@RequestBody @Valid JobDTO jobDTO) {


        System.out.println(">>>>>>>>>>>>>>>>>>>>"+jobDTO);

        try {
            int res = jobService.saveJob(jobDTO);

            switch (res){
                case VarList.Created ->{


                 emailService.   sendJobPostConfirmationEmail(
                            jobDTO.getEmail(),
                            jobDTO.getCompany().getCompany_name(),
                            jobDTO.getJobTitle(),
                            jobDTO.getJobTitle(),
                            String.valueOf(jobDTO.getJobId())
                    );

                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created,"success",jobDTO));
                }

                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable,"url already added!!!",jobDTO));
                }

                case VarList.Not_Found -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseDTO(VarList.Not_Found,"url not found!!!",jobDTO));
                }

                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway,"Error",jobDTO));
                }

            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",jobDTO));
        }
        }


    @PutMapping("/update")
     public ResponseEntity<ResponseDTO>updateJob(@RequestBody @Valid JobDTO jobDTO) {

            try {
                int res = jobService.updateJob(jobDTO);

                switch (res){
                    case VarList.Created ->{
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseDTO(VarList.Created,"success",jobDTO));
                    }

                    case VarList.Not_Acceptable -> {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                .body(new ResponseDTO(VarList.Not_Acceptable,"url already added!!!",jobDTO));
                    }

                    case VarList.Not_Found -> {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseDTO(VarList.Not_Found,"url not found!!!",jobDTO));
                    }

                    default -> {
                        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body(new ResponseDTO(VarList.Bad_Gateway,"Error",jobDTO));
                    }

                }
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",jobDTO));
            }
        }


    @GetMapping("/getAll")
    public List<JobDTO> getAllUsers() {

        try {
            return jobService.getAllJobs();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobDTO>> getJobsByCompanyId(@PathVariable UUID companyId) {

        List<JobDTO> jobDtos = jobService.getJobsByUserId(companyId);
        System.out.println(">>>>>>>>>>>>>"+jobDtos);

        if (jobDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jobDtos);
    }


    @GetMapping("/getJobsByJobId/{jobId}")

    public List<JobDTO> getJobsByJobId(@PathVariable UUID jobId) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+jobId);
        return jobService.getJobsByJobId(jobId);
    }



    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchJobs(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String jobType) {

        logger.info("Received search request with params - country: {}, jobTitle: {}, jobType: {}",
                country, jobTitle, jobType);

        List<JobDTO> jobDTOs = jobService.searchJobs(country, jobTitle, jobType);

        logger.info("Search returned {} results", jobDTOs.size());

        ResponseDTO response = new ResponseDTO();

        if (jobDTOs.isEmpty()) {
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("No jobs found for the provided search criteria.");
            response.setData(null);
            logger.warn("No jobs found with the provided criteria");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setCode(HttpStatus.OK.value());
        response.setMessage("Jobs found successfully.");
        response.setData(jobDTOs);
        return ResponseEntity.ok(response);
    }






    @GetMapping("/search-page")
    public ResponseEntity<List<JobDTO>> searchJobs(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) List<String> type
    ) {
        List<JobDTO> jobs = jobService.searchJobs(category, keyword, location, type);
        System.out.println("search"+jobs);



        if (jobs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(jobs);
        }
        System.out.println("search>>>>>>>>>>>>>>>>>>>"+jobs);
        System.out.println("Received category: " + category);
        System.out.println("Received keyword: " + keyword);
        System.out.println("Received location: " + location);
        System.out.println("Received type: " + type);

        return ResponseEntity.ok(jobs);
    }

}




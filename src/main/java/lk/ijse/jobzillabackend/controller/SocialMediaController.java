package lk.ijse.jobzillabackend.controller;

import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.dto.SocialMediaDTO;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.SocialMedia;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.service.SocialMediaService;
import lk.ijse.jobzillabackend.util.JwtUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/socialMedia")
public class SocialMediaController {


    private SocialMediaService socialMediaService;
    private CompanyRepository companyRepository;
    private JwtUtil jwtUtil;

    public SocialMediaController(SocialMediaService socialMediaService, JwtUtil jwtUtil) {
        this.socialMediaService = socialMediaService;
        this.jwtUtil = jwtUtil;
    }


    @PreAuthorize("hasAuthority('EMPLOYER')")
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveSocialMedia(@RequestBody @Valid SocialMediaDTO socialMediaDTO) {


        System.out.println(">>>>>>>>>>>>>>>>."+socialMediaDTO);
                try{
                    int res = socialMediaService.saveSocialMedia(socialMediaDTO);
                    System.out.println("sid"+socialMediaDTO.getSid());
                    switch (res){
                        case VarList.Created ->{
                            return ResponseEntity.status(HttpStatus.CREATED)
                                    .body(new ResponseDTO(VarList.Created,"success",socialMediaDTO));
                        }

                        case VarList.Not_Acceptable -> {
                             return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                     .body(new ResponseDTO(VarList.Not_Acceptable,"url already added!!!",socialMediaDTO));
                        }

                        case VarList.Not_Found -> {
                               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                       .body(new ResponseDTO(VarList.Not_Found,"url not found!!!",socialMediaDTO));
                        }

                        default -> {
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                    .body(new ResponseDTO(VarList.Bad_Gateway,"Error",socialMediaDTO));
                        }

                    }
                }catch (Exception e){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseDTO(VarList.Internal_Server_Error,"Internal Server Error",e.getMessage()));
                }
    }
}

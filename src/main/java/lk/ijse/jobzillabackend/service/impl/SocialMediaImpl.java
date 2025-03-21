package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.SocialMediaDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.SocialMedia;
import lk.ijse.jobzillabackend.repo.SocialMediaRepository;
import lk.ijse.jobzillabackend.service.SocialMediaService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SocialMediaImpl implements SocialMediaService {

    @Autowired
    SocialMediaRepository socialMediaRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public int saveSocialMedia(SocialMediaDTO socialMediaDTO) {
      if (socialMediaRepository.existsById(socialMediaDTO.getSid())){
          System.out.println(socialMediaDTO.getSid());
          return VarList.Not_Acceptable;
      }else {
          socialMediaRepository.save(modelMapper.map(socialMediaDTO, SocialMedia.class));
          System.out.println(socialMediaDTO.getSid());
          return VarList.Created;
      }
    }

    @Override
    public int updateSocialMedia(SocialMediaDTO socialMediaDTO) {
        if (!socialMediaRepository.existsById(socialMediaDTO.getSid())) {
               return VarList.Not_Acceptable;
        } else {
            socialMediaRepository.save(modelMapper.map(socialMediaDTO, SocialMedia.class));
            System.out.println(socialMediaDTO.getSid());
            return VarList.Created;
        }

    }

    @Override
    public int deleteSocialMedia(int id) {
        return 0;
    }

    @Override
    @Transactional
    public List<SocialMediaDTO> getAll() {
        return modelMapper.map(socialMediaRepository.findAll(),new TypeToken<List<SocialMediaDTO>>(){}.getType());
    }
}

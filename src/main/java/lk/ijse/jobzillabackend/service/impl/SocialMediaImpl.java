package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.SocialMediaDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.SocialMedia;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.SocialMediaRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public int saveSocialMedia(SocialMediaDTO socialMediaDTO) {
        if (socialMediaRepository.existsById(socialMediaDTO.getSid())) {
            System.out.println("Social Media entry already exists with ID: " + socialMediaDTO.getSid());
            return VarList.Not_Acceptable;
        } else {
            SocialMedia socialMedia = modelMapper.map(socialMediaDTO, SocialMedia.class);

            User user = userRepository.findById(socialMediaDTO.getUser().getUid())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + socialMediaDTO.getUser().getUid()));
            socialMedia.setUser(user);

            socialMediaRepository.save(socialMedia);
            System.out.println("Saved Social Media with ID: " + socialMedia.getSid());
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

package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.SocialMediaDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;

import java.util.List;

public interface SocialMediaService {

    int saveSocialMedia(SocialMediaDTO socialMediaDTO);
    int updateSocialMedia(SocialMediaDTO socialMediaDTO);
    int deleteSocialMedia(int id);
    List<UserDTO> getAll();
}

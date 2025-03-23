package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    int saveCandidate(CandidateDTO candidateDTO, MultipartFile file);
    int updateCandidate(CandidateDTO candidateDTO);
    int deleteCandidate(String id);
    List<CandidateDTO> getAll();
}

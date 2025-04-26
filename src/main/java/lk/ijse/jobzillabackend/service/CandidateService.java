package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CandidateService {
    int saveCandidate(CandidateDTO candidateDTO, MultipartFile file);
    int updateCandidate(CandidateDTO candidateDTO,MultipartFile file);
    int deleteCandidate(String id);
    List<CandidateDTO> getAll();

    List<CandidateDTO> getCandidatesByCandId(UUID candId);

    List<CandidateDTO> getCandidateByUserId(UUID userId);

    long activeCandidatesCount();

}


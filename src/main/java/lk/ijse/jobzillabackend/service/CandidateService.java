package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.UserDTO;
import lk.ijse.jobzillabackend.entity.Candidate;

import java.util.List;

public interface CandidateService {
    int saveCandidate(CandidateDTO candidateDTO);
    int updateCandidate(CandidateDTO candidateDTO);
    int deleteCandidate(String id);
    List<CandidateDTO> getAll();
}

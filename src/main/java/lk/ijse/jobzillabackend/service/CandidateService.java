package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.entity.Candidate;

public interface CandidateService {
    int saveCandidate(CandidateDTO candidateDTO);
}

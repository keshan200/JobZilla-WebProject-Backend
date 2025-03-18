package lk.ijse.jobzillabackend.service.impl;

import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.User;
import lk.ijse.jobzillabackend.repo.CandidateRepository;
import lk.ijse.jobzillabackend.repo.CompanyRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CandidateService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public int saveCandidate(CandidateDTO candidateDTO) {

        if (candidateDTO.getCand_id() != null && candidateRepository.existsById(candidateDTO.getCand_id())) {
            return VarList.Not_Acceptable;

        }

        User user = userRepository.findById(candidateDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Candidate candidate = modelMapper.map(candidateDTO, Candidate.class);
        candidateRepository.save(candidate);
        return VarList.Created;
    }
}

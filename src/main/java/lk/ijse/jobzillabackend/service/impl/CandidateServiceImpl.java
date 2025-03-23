package lk.ijse.jobzillabackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.repo.CandidateRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CandidateService;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Override
    public int saveCandidate(CandidateDTO candidateDTO,MultipartFile file) {

       /* if (candidateDTO.getCand_id() != null && candidateRepository.existsById(candidateDTO.getCand_id())) {
            return VarList.Not_Acceptable;
        }

        userRepository.findById(candidateDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Candidate candidate = modelMapper.map(candidateDTO, Candidate.class);
        candidateRepository.save(candidate);
        return VarList.Created;*/

        if (candidateDTO.getCand_id() != null && candidateRepository.existsById(candidateDTO.getCand_id())) {
            return VarList.Not_Acceptable;
        }

        userRepository.findById(candidateDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Candidate candidate = modelMapper.map(candidateDTO, Candidate.class);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = candidateDTO.getUser().getUid() + "_" + file.getOriginalFilename();
                String uploadDir = "candidates/" + candidateDTO.getUser().getUid();
                FileUploadUtil.saveFile(uploadDir, fileName, file);
                candidate.setImg("uploads/" + uploadDir + "/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }
        candidateRepository.save(candidate);
        return VarList.Created;
    }




    @Override
    public int updateCandidate(CandidateDTO candidateDTO) {
        if (candidateDTO.getCand_id() == null || !candidateRepository.existsById(candidateDTO.getCand_id())) {
            return VarList.Not_Found;
        }

        userRepository.findById(candidateDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User Account not found"));

        Candidate existingCandidate = candidateRepository.findById(candidateDTO.getCand_id())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        modelMapper.map(candidateDTO, existingCandidate);

        candidateRepository.save(existingCandidate);
        return VarList.Created;
    }

    @Override
    public int deleteCandidate(String id) {
        return 0;
    }

    @Override
    public List<CandidateDTO> getAll() {
        return modelMapper.map(candidateRepository.findAll(),new TypeToken<List<CandidateDTO>>(){}.getType());
    }
}

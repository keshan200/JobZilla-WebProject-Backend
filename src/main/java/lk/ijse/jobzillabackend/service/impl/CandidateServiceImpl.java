package lk.ijse.jobzillabackend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lk.ijse.jobzillabackend.dto.CandidateDTO;
import lk.ijse.jobzillabackend.dto.CompanyDTO;
import lk.ijse.jobzillabackend.dto.JobDTO;
import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.Company;
import lk.ijse.jobzillabackend.entity.Job;
import lk.ijse.jobzillabackend.repo.CandidateRepository;
import lk.ijse.jobzillabackend.repo.UserRepository;
import lk.ijse.jobzillabackend.service.CandidateService;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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

        if (candidateDTO.getCand_id() != null && candidateRepository.existsById(candidateDTO.getCand_id())) {
            return VarList.Not_Acceptable;
        }

        userRepository.findById(candidateDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Candidate candidate = modelMapper.map(candidateDTO, Candidate.class);


        if (file != null && !file.isEmpty()) {
            try {
                String fileName = candidateDTO.getUser().getUid() + "_" + file.getOriginalFilename();
                String uploadDir = "candidate/";
                FileUploadUtil.saveFile(uploadDir, fileName, file);
                String filePath = uploadDir  + fileName;
                candidate.setImg(filePath);
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }
        Candidate saveCandidate = candidateRepository.save(candidate);
        candidateDTO.setImg(saveCandidate.getImg());
        return VarList.Created;
    }




    @Override
    public int updateCandidate(CandidateDTO candidateDTO, MultipartFile file) {

        if (candidateDTO.getCand_id() == null || !candidateRepository.existsById(candidateDTO.getCand_id())) {
            return VarList.Not_Found;
        }

        userRepository.findById(candidateDTO.getUser().getUid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Candidate existingCandidate = modelMapper.map(candidateDTO, Candidate.class);


        candidateRepository.save(existingCandidate);
        return VarList.Created;
    }

    @Override
    public int deleteCandidate(String id) {
        return 0;
    }

    @Override
    @Transactional
    public List<CandidateDTO> getAll() {
        List<Candidate> candidates = candidateRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();

        return candidates.stream()
                .map(candidate -> {
                    CandidateDTO dto = objectMapper.convertValue(candidate, CandidateDTO.class);

                    if (candidate.getImg() != null && !candidate.getImg().isEmpty()) {
                        String imageUrl = "http://localhost:8080/uploads/" + candidate.getImg();
                        dto.setImg(imageUrl);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }





    @Override
    @Transactional
    public List<CandidateDTO> getCandidatesByCandId(UUID candId) {
        List<Candidate> cand = candidateRepository.findByCandId(candId);
        ObjectMapper objectMapper = new ObjectMapper();
        return cand.stream()
                .map(candidate -> {
                    CandidateDTO dto = objectMapper.convertValue(candidate, CandidateDTO.class);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<CandidateDTO> getCandidateByUserId(UUID userId) {
        Optional<Candidate> candidates = candidateRepository.findByUser_Uid(userId);
        if (candidates.isEmpty()) {
            return Collections.emptyList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return candidates.stream()
                .map(candidate -> objectMapper.convertValue(candidate, CandidateDTO.class))
                .collect(Collectors.toList());
    }

}

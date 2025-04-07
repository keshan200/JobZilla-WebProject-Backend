package lk.ijse.jobzillabackend.service.impl;

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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
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
                String uploadDir = "candidate/";
                FileUploadUtil.saveFile(uploadDir, fileName, file);
                String filePath = uploadDir  + fileName;
                candidate.setImg(filePath);
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }
        candidateRepository.save(candidate);
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

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = candidateDTO.getUser().getUid() + "_" + file.getOriginalFilename();
                String uploadDir = "candidates/" + candidateDTO.getUser().getUid();
                FileUploadUtil.saveFile(uploadDir, fileName, file);
                existingCandidate.setImg("uploads/" + uploadDir + "/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("File saving failed: " + e.getMessage());
            }
        }
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
        return candidates.stream()
                .map(candidate -> {
                    CandidateDTO dto = modelMapper.map(candidate, CandidateDTO.class);
                    if (candidate.getImg() != null && !candidate.getImg().isEmpty()) {
                        dto.setImg("http://localhost:8080/uploads/" + candidate.getImg());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CandidateDTO> getCandidatesByCandId(UUID candId) {
        List<Candidate> cand = candidateRepository.findByCandId(candId);
        ModelMapper modelMapper = new ModelMapper();
        return cand.stream()
                .map(candidate -> modelMapper.map(candidate, CandidateDTO.class))
                .toList();
    }


    @Override
    @Transactional
    public List<CandidateDTO> getCandidateByUserId(UUID userId) {
        Optional<Candidate> candidates = candidateRepository.findByUser_Uid(userId);
        return candidates.stream()
                .map(candidate -> modelMapper.map(candidate, CandidateDTO.class))
                .toList();
    }

}

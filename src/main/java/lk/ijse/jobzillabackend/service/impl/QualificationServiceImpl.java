package lk.ijse.jobzillabackend.service.impl;

import jakarta.validation.Valid;
import lk.ijse.jobzillabackend.dto.QualificationDTO;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.repo.QualificationRepository;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class QualificationServiceImpl implements QualificationService {


    @Autowired
    QualificationRepository qualificationRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public int saveQualification(QualificationDTO qualificationDTO) {

        if (qualificationDTO.getQualificationName() == null || qualificationDTO.getQualificationName().isEmpty()) {
            return VarList.Bad_Request;
        }

        if (qualificationRepository.existsById(qualificationDTO.getQul_id())){
            return VarList.Not_Acceptable;
        }else {
            qualificationRepository.save(modelMapper.map(qualificationDTO, Qualification.class));
            return VarList.Created;
        }
    }
}

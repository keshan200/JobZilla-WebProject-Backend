package lk.ijse.jobzillabackend.service.impl;

import lk.ijse.jobzillabackend.dto.QualificationDTO;
import lk.ijse.jobzillabackend.entity.Qualification;
import lk.ijse.jobzillabackend.repo.QualificationRepository;
import lk.ijse.jobzillabackend.service.QualificationService;
import lk.ijse.jobzillabackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QualificationServiceImpl implements QualificationService {


    @Autowired
    QualificationRepository qualificationRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public int saveQualification(QualificationDTO qualificationDTO) {
        if (qualificationDTO.getQul_id() == null || qualificationDTO.getQul_name().isEmpty()) {
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

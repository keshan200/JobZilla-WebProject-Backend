package lk.ijse.jobzillabackend.service;

import lk.ijse.jobzillabackend.dto.QualificationDTO;
import lk.ijse.jobzillabackend.entity.Qualification;

public interface QualificationService {

    int saveQualification(QualificationDTO qualificationDTO);
}

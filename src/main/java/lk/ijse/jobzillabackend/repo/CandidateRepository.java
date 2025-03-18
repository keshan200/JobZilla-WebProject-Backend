package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.Candidate;
import lk.ijse.jobzillabackend.entity.SocialMedia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {

}

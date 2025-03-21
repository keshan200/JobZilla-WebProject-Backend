package lk.ijse.jobzillabackend.repo;

import lk.ijse.jobzillabackend.entity.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface SocialMediaRepository extends JpaRepository<SocialMedia, Integer> {

}

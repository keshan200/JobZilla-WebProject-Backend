package lk.ijse.jobzillabackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SocialMedia{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sid;
    private String platform;
    private String url;

    @ManyToOne
    @JoinColumn(name = "company_id",referencedColumnName = "cid", nullable = false)
    @JsonBackReference("company-socialMediaProfiles")
    private Company company;

}

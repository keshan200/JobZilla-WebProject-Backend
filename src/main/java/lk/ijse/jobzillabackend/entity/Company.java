package lk.ijse.jobzillabackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cid", columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID cid;

    @Column(nullable = false)
    private String company_name;
    private String country;
    private String city;
    private String full_address;
    private String description;
    private String Logo_img;
    private String background_img;
    private String est_since;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialMedia> socialMediaProfiles;


}

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

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String full_address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String Logo_img;

    @Column(nullable = false)
    private String background_img;

    @Column(nullable = false)
    private String est_since;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<SocialMedia> socialMediaProfiles;

    @ManyToMany
    private List<Job>job;


}

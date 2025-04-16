package lk.ijse.jobzillabackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String website;

    @Column(nullable = false)
    private String Logo_img;

    private String background_img;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> image_collection = new ArrayList<>();

    @Column(nullable = false)
    private String est_since;

    @Column(nullable = false)
    private String company_type;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("company-socialMedia")
    private List<SocialMedia> socialMediaProfiles;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER )
    @JsonManagedReference("company-job")
    private List<Job> jobs;













}

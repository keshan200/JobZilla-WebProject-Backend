package lk.ijse.jobzillabackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.*;

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

    private String website;

    @Column(nullable = false)
    private String Logo_img;

    private String background_img;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> image_collection = new ArrayList<>();

    @Column(nullable = false)
    private String est_since;



    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = false,fetch = FetchType.EAGER)
    private List<SocialMedia> socialMediaProfiles;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Job> jobs;

    @ManyToMany
    @JsonIgnore
    private List<Application> applications;




}

package lk.ijse.jobzillabackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Candidate implements Serializable  {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID cand_id;

    private String img;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    private String experience;

    private String website;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String full_address;

    @Column(nullable = false)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> skills = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid" )
    private User user;


    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonManagedReference("candidate-application")
    private List<Application> applications;
}

package lk.ijse.jobzillabackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lk.ijse.jobzillabackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Job implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID jobId;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String jobCategory;

    @Column(nullable = false)
    private String jobType;

    private String offeredSalary;

    @Column(nullable = false)
    private String experience;

    @Column(nullable = false)
    private String qualification;

    private String gender;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String location;


    @Column(nullable = false)
    private String email;

    private String website;

    @Column(nullable = false)
    private String est_since;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private String fulladdress;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> responsibilities = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> requirements = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.ACTIVE;
        }
    }


    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "cid", nullable = false)
    @JsonIgnore
    private Company company;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Application> applications;
}

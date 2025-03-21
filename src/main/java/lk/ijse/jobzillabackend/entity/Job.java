package lk.ijse.jobzillabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String email;

    private String website;

    @Column(nullable = false)
    private String est_since;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToMany(mappedBy = "job",cascade = CascadeType.ALL)
    private List<Company> company;
}

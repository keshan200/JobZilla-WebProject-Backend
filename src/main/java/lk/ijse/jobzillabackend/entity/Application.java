package lk.ijse.jobzillabackend.entity;

import jakarta.persistence.*;
import lk.ijse.jobzillabackend.enums.ApplicationStatus;
import lk.ijse.jobzillabackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String resume;

    @Column(nullable = false)
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
    }
}

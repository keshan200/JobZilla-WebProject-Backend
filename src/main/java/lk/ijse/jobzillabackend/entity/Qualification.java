package lk.ijse.jobzillabackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "qul_id", columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    private UUID qul_id;

    @Column(nullable = false)
    private String qualificationName;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private int yearOfCompletion;

    private String grade;

    private String description;


    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}

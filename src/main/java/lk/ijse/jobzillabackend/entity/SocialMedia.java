package lk.ijse.jobzillabackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sid", columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sid;


    private String platform;
    private String url;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

}

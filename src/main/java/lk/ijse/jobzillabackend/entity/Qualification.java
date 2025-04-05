package lk.ijse.jobzillabackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID qul_id;

    @Column(nullable = false)
    private String qul_name;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String year;
    private String description;




}

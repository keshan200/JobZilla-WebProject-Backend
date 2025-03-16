package lk.ijse.jobzillabackend.entity;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import jakarta.persistence.*;
import lk.ijse.jobzillabackend.util.ActiveStatusConverter;
import lk.ijse.jobzillabackend.validations.ValidRoleBasedEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;


import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidRoleBasedEmail
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uid", columnDefinition = "VARCHAR(36)", unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uid;


    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int mobile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    @Convert(converter = ActiveStatusConverter.class)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'Active'")
    private boolean active;
}

package lk.ijse.jobzillabackend.entity;


import jakarta.persistence.*;
import lk.ijse.jobzillabackend.enums.Status;
import lk.ijse.jobzillabackend.enums.UserRole;
import lk.ijse.jobzillabackend.validations.ValidRoleBasedEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidRoleBasedEmail
public class User{

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


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;








    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.ACTIVE;
        }
    }
}

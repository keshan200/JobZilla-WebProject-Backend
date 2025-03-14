package lk.ijse.jobzillabackend.entity;


import jakarta.persistence.*;
import lk.ijse.jobzillabackend.util.ActiveStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int mobile;

    @Column(nullable = false)
    private String role;


    @Convert(converter = ActiveStatusConverter.class)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'Active'")
    private boolean active;
}

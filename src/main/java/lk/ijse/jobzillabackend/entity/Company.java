package lk.ijse.jobzillabackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cid;

    @Column(nullable = false)
    private String company_name;
    private String country;
    private String city;
    private String full_address;
    private int mobile_number;
    private String description;
    private String Logo_img;
    private String background_img;
    private String est_since;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;


}

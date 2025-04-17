package lk.ijse.jobzillabackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SocialMedia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sid;
    private String platform;
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "uid")
    @JsonBackReference("user-socialMedia")
    private User user;

}

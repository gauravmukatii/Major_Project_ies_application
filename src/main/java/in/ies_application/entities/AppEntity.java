package in.ies_application.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "IES_APP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseNum;
    private String fullName;
    private String email;
    private String gender;
    private LocalDate dob;
    private String phno;
    private Long ssn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

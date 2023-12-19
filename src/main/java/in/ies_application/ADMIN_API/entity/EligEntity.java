package in.ies_application.ADMIN_API.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "ELIG_DTLS")
public class EligEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer edgTraceId;
    private String planStatus;
    private Double benefitAmt;
}

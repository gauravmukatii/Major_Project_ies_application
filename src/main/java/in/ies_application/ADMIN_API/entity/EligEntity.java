package in.ies_application.ADMIN_API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ELIG_DTLS")
public class EligEntity {
    private String planStatus;
    private Double benefitAmt;
}

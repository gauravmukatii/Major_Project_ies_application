package in.ies_application.ADMIN_API.bindings;

import lombok.*;

import java.time.LocalDate;

@Data
public class UserAccForm {

    private Long userId;
    private String fullName;
    private String email;
    private Long mobileNo;
    private String gender;
    private LocalDate dob;
    private Long ssn;
    private String accStatus;
    private String activeSw;
    private Integer roleId;
}

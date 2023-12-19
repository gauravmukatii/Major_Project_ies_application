package in.ies_application.ADMIN_API.exception;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class AppException {
    private String exCode;
    private String exDesc;
    private LocalDateTime exDate;
}

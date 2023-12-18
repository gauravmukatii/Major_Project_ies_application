package in.ies_application.ADMIN_API.bindings;

import lombok.Data;

@Data
public class UnlockAccForm {
    private String email;
    private String tempPwd;
    private String newPwd;
    private String confirmPwd;
}

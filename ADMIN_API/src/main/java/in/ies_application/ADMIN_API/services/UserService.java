package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.DashboardCard;
import in.ies_application.ADMIN_API.bindings.LoginForm;
import in.ies_application.ADMIN_API.bindings.UnlockAccForm;
import in.ies_application.ADMIN_API.bindings.UserAccForm;

public interface UserService {

    public String userLogin(LoginForm loginForm);
    public boolean recoverPwd(String email);
    public DashboardCard fetchDashboardInfo();

    public UserAccForm getUserByEmail(String email);

    public String unlockUserAccount(UnlockAccForm unlockAccForm);
}

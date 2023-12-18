package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.UnlockAccForm;
import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.entity.UserEntity;

import java.util.List;

public interface AccountService {

    public boolean createUserAccount(UserAccForm form);
    public List<UserAccForm> fetchUserAccounts();

    public UserAccForm getUserAccById(Long accId);

    public String changeAccStatus(Long accId, String status);

}

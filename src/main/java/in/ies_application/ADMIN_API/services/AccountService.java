package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.entity.UserEntity;

import java.util.List;

public interface AccountService {

    public boolean createUserAccount(UserAccForm form);
    public List<UserAccForm> fetchUserAccounts();

    public UserAccForm getUserAccById(Integer accId);

    public String changeAccStatus(Integer accId, String status);

}

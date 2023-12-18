package in.ies_application.ADMIN_API.repos;

import in.ies_application.ADMIN_API.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    @Query("update UserEntity set accStatus=:status where userId=:userId")
    public Integer updateAccStatus(Long userId, String status);
    public UserEntity findByEmail(String email);
    public UserEntity findByEmailAndPassword(String email, String pwd);
}

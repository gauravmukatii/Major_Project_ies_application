package in.ies_application.ADMIN_API.repos;

import feign.Param;
import in.ies_application.ADMIN_API.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Query("update UserEntity set activeSw=:status where userId=:userId")
    public Integer updateAccStatus(@Param("userId") Long userId, @Param("status") String status);
    public UserEntity findByEmail(String email);
    public UserEntity findByEmailAndPwd(String email, String pwd);
}

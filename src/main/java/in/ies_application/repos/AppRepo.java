package in.ies_application.repos;

import in.ies_application.entities.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepo extends JpaRepository<AppEntity, Long> {
    @Query("from AppEntity")
    public List<AppEntity> fetchUserApps();

    @Query("from AppEntity where user.userId = :userId")
    public List<AppEntity> fetchCwApps(Integer userId);
}

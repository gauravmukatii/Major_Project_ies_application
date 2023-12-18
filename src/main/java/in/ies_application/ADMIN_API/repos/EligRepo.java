package in.ies_application.ADMIN_API.repos;

import in.ies_application.ADMIN_API.entity.EligEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligRepo extends JpaRepository<EligEntity, Integer> {
}

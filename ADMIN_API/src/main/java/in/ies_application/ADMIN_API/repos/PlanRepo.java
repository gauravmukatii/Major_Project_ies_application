package in.ies_application.ADMIN_API.repos;

import feign.Param;
import in.ies_application.ADMIN_API.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepo extends JpaRepository<PlanEntity, Integer> {

    @Query("update PlanEntity set activeSw = :status where planId = :planId")
    public Integer updatePlanStatus(@Param("planId") Integer planId, @Param("status") String status);

}

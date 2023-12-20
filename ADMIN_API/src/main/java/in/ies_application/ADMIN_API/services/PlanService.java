package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.PlanForm;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlanService {

    public boolean createPlan(PlanForm form);
    public List<PlanForm> viewAllPlans();
    public PlanForm getPlanById(Integer planId);
    public String changePlanStatus(Integer planId, String status);
}

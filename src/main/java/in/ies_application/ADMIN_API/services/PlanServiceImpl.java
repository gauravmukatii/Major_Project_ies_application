package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.PlanForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public boolean createPlan(PlanForm form) {
        return false;
    }

    @Override
    public List<PlanForm> viewAllPlans() {
        return null;
    }

    @Override
    public PlanForm getPlanById(Integer planId) {
        return null;
    }

    @Override
    public String changePlanStatus(Integer planId, String status) {
        return null;
    }
}

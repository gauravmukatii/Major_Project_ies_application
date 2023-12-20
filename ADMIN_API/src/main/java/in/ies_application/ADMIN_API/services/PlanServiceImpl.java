package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.PlanForm;
import in.ies_application.ADMIN_API.entity.PlanEntity;
import in.ies_application.ADMIN_API.repos.PlanRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepo planRepo;
    @Override
    public boolean createPlan(PlanForm form) {
        PlanEntity planEntity = new PlanEntity();
        BeanUtils.copyProperties(form, planEntity);
        planEntity.setActiveSw("Y");
        planRepo.save(planEntity);

        return true;
    }

    @Override
    public List<PlanForm> viewAllPlans() {

        List<PlanEntity> entities = planRepo.findAll();
        List<PlanForm> plans = new ArrayList<>();

        for(PlanEntity entity: entities){
            PlanForm plan = new PlanForm();
            BeanUtils.copyProperties(entity, plan);
            plans.add(plan);
        }

        return plans;
    }

    @Override
    public PlanForm getPlanById(Integer planId) {
        Optional<PlanEntity> optional = planRepo.findById(planId);
        if(optional.isPresent()){
            PlanForm plan = new PlanForm();
            BeanUtils.copyProperties(optional,plan);
            return plan;
        }
        return null;
    }

    @Override
    public String changePlanStatus(Integer planId, String status) {
        int cnt = planRepo.updatePlanStatus(planId, status);

        if(cnt > 0){
            return "Status Changed";
        }
        return "Failed to change Status";
    }
}

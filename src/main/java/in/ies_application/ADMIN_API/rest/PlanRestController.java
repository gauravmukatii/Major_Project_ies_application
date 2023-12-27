package in.ies_application.ADMIN_API.rest;

import in.ies_application.ADMIN_API.bindings.PlanForm;
import in.ies_application.ADMIN_API.repos.PlanRepo;
import in.ies_application.ADMIN_API.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class PlanRestController {

    @Autowired
    private PlanService planService;

    @PostMapping("/createplan")
    public ResponseEntity<String> createPlan(@RequestBody PlanForm form){
        boolean status = planService.createPlan(form);

        if(status){
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allplans")
    public ResponseEntity<List<PlanForm>> getAllPlans(){
        List<PlanForm> plans = planService.viewAllPlans();

        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @PutMapping("/changeplan/{planId}/{status}")
    public ResponseEntity<List<PlanForm>> updatePlanStatus(@PathVariable("planId") int planId,@PathVariable("status") String status){
        planService.changePlanStatus(planId, status);
        List<PlanForm> plans = planService.viewAllPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
}

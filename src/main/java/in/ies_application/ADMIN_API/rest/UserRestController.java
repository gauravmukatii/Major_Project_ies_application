package in.ies_application.ADMIN_API.rest;

import in.ies_application.ADMIN_API.bindings.DashboardCard;
import in.ies_application.ADMIN_API.bindings.LoginForm;
import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.services.AccountService;
import in.ies_application.ADMIN_API.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    private Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public String userLogin(@RequestBody LoginForm loginForm){
        logger.info("Received login request for email: {}", loginForm.getEmail());
        String status = userService.userLogin(loginForm);
        if(status.equals("success")){
            logger.info("Login successful for user with email: {}", loginForm.getEmail());
            return "redirect:/dashboard?email="+loginForm.getEmail();
        }else{
            logger.warn("Login failed for user with email: {}", loginForm.getEmail());
            return status;
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardCard> buildDashboard(@RequestParam("email") String email){
        logger.info("Building dashboard for user with email: {}", email);
         UserAccForm user = userService.getUserByEmail(email);
         DashboardCard card = userService.fetchDashboardInfo();
         card.setUser(user);
         logger.info("Dashboard built successfully for user with email: {}", email);
         return new ResponseEntity<>(card, HttpStatus.OK);
    }

}

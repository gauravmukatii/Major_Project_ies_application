package in.ies_application.ADMIN_API.rest;

import in.ies_application.ADMIN_API.bindings.DashboardCard;
import in.ies_application.ADMIN_API.bindings.LoginForm;
import in.ies_application.ADMIN_API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String userLogin(@RequestBody LoginForm loginForm){
        String status = userService.userLogin(loginForm);
        if(status.equals("success")){
            return "redirect:/dashboard";
        }else{
            return status;
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardCard> buildDashboard(){
         DashboardCard card = userService.fetchDashboardInfo();
         return new ResponseEntity<>(card, HttpStatus.OK);
    }


}

package in.ies_application.ADMIN_API.rest;

import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AccountRestController {

    private Logger logger = LoggerFactory.getLogger(UserRestController.class);
    @Autowired
    private AccountService accountService;

    @PostMapping("/user")
    public ResponseEntity<String> createAccount(@RequestBody UserAccForm userAccForm){
         logger.debug("Account Creation Process Started...");
         boolean status = accountService.createUserAccount(userAccForm);
        logger.debug("Account Creation Process Completed...");
         if(status){
             logger.info("Account Created Successfully!!");
             return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
         }else{
             logger.info("Account Creation failed!!");
             return new ResponseEntity<>("Account Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserAccForm>> getUsers(){
        logger.debug("Fetching user Accounts process started...");
        List<UserAccForm> users = accountService.fetchUserAccounts();
        logger.debug("Fetching user Accounts process completed...");
        logger.info("User Accounts Fetched Successfully ... ");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<UserAccForm> getUser(@PathVariable("userId") Long userid){
        UserAccForm user = accountService.getUserAccById(userid);
        logger.info("User Account Fetched Successfully ... ");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/{status}")
    public ResponseEntity<List<UserAccForm>> updateUserAcc(@PathVariable("userId") Long userId, @PathVariable("status") String status){
        logger.debug("User Account status Update Process Started ... ");
        accountService.changeAccStatus(userId, status);
        logger.debug("User Account status Update Process Completed ... ");
        logger.info("User Account status Updated Successfully ... ");
        List<UserAccForm> users = accountService.fetchUserAccounts();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

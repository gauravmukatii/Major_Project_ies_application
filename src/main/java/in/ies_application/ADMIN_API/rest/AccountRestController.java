package in.ies_application.ADMIN_API.rest;

import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/user")
    public ResponseEntity<String> createAccount(@RequestBody UserAccForm userAccForm){
         boolean status = accountService.createUserAccount(userAccForm);
         if(status == true){
             return new ResponseEntity<>("Account Created", HttpStatus.CREATED);
         }else{
             return new ResponseEntity<>("Account Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserAccForm>> getUsers(){
        List<UserAccForm> users = accountService.fetchUserAccounts();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<UserAccForm> getUser(@PathVariable("userId") Long userid){
        UserAccForm user = accountService.getUserAccById(userid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/{status}")
    public ResponseEntity<List<UserAccForm>> updateUserAcc(@PathVariable("userId") Long userId, @PathVariable("status") String status){
        accountService.changeAccStatus(userId, status);
        List<UserAccForm> users = accountService.fetchUserAccounts();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

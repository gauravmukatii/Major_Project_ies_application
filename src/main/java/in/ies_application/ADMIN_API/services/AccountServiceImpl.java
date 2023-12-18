package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.entity.UserEntity;
import in.ies_application.ADMIN_API.repos.UserRepo;
import in.ies_application.ADMIN_API.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public boolean createUserAccount(UserAccForm accForm) {

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(accForm, entity);

        //set random pwd
        entity.setPwd(generatePwd());

        //set acc status
        entity.setAccStatus("LOCKED");
        entity.setActiveSw("Y");
        userRepo.save(entity);

        //send email
        String subject = "";
        String body = "";
        return emailUtils.sendEmail(subject, body, accForm.getEmail());
    }

    @Override
    public List<UserAccForm> fetchUserAccounts() {
        return null;
    }

    @Override
    public UserAccForm getUserAccById(Integer accId) {
        return null;
    }

    @Override
    public String changeAccStatus(Integer accId, String status) {
        return null;
    }

    private String generatePwd(){
        // create a string of all characters
        String UpperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphanumeric = UpperCaseAlphabet + LowerCaseAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 6;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphanumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphanumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString;
    }
}
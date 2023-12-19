package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.UnlockAccForm;
import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.entity.UserEntity;
import in.ies_application.ADMIN_API.repos.UserRepo;
import in.ies_application.ADMIN_API.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

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
        String subject = "User Registration";
        String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
        return emailUtils.sendEmail(subject, body, accForm.getEmail());
    }

    @Override
    public List<UserAccForm> fetchUserAccounts() {
        List<UserEntity> userEntities = userRepo.findAll();
        List<UserAccForm> users = new ArrayList<>();

        for(UserEntity userEntity: userEntities){
            UserAccForm user = new UserAccForm();
            BeanUtils.copyProperties(userEntity, user);
            users.add(user);
        }
        return users;
    }

    @Override
    public UserAccForm getUserAccById(Long accId) {
        Optional<UserEntity> optional = userRepo.findById(accId);
        if(optional.isPresent()){
            UserEntity userEntity = optional.get();
            UserAccForm user = new UserAccForm();
            BeanUtils.copyProperties(userEntity, user);
            return user;
        }
        return null;
    }

    @Override
    public String changeAccStatus(Long accId, String status) {
        int cnt = userRepo.updateAccStatus(accId, status);
        if(cnt>0){
            return "Status Changed";
        }
        return "Failed to Change Status";
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

    private String readEmailBody(String filename, UserEntity user) {
        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                line = line.replace("${FNAME}", user.getFullName());
                line = line.replace("${TEMP_PWD}", user.getPwd());
                line = line.replace("${EMAIL}", user.getEmail());
                sb.append(line);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

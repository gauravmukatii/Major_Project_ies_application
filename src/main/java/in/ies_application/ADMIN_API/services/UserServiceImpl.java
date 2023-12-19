package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.DashboardCard;
import in.ies_application.ADMIN_API.bindings.LoginForm;
import in.ies_application.ADMIN_API.bindings.UnlockAccForm;
import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.entity.EligEntity;
import in.ies_application.ADMIN_API.entity.UserEntity;
import in.ies_application.ADMIN_API.repos.EligRepo;
import in.ies_application.ADMIN_API.repos.PlanRepo;
import in.ies_application.ADMIN_API.repos.UserRepo;
import in.ies_application.ADMIN_API.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private EligRepo eligRepo;
    @Autowired
    private PlanRepo planRepo;
    @Override
    public String userLogin(LoginForm loginForm) {
        UserEntity user = userRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());

        if(user == null){
            return "Invalid Credentials";
        }else if("Y".equals(user.getActiveSw()) && "UNLOCKED".equals(user.getAccStatus())){
            return "success";
        }else{
            return "Account Locked/Inactive";
        }
    }

    @Override
    public boolean recoverPwd(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        if(userEntity != null){
            String subject = "Recover Pwd";
            String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", userEntity);
            return emailUtils.sendEmail(subject, body, userEntity.getEmail());
        }
        return false;
    }

    @Override
    public DashboardCard fetchDashboardInfo() {
        long plansCnt = planRepo.count();

        List<EligEntity> eligList = eligRepo.findAll();

        Long approvedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals("AP")).count();
        Long denielCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals("DN")).count();
        Double benefitAmt = eligList.stream().mapToDouble(ed -> ed.getBenefitAmt()).sum();

        DashboardCard card = new DashboardCard();
        card.setPlansCnt(plansCnt);
        card.setApproveCnt(approvedCnt);
        card.setDenielCnt(denielCnt);
        card.setBenefitAmtGiven(benefitAmt);

        return card;
    }

    @Override
    public UserAccForm getUserByEmail(String email) {
        UserEntity entity = userRepo.findByEmail(email);

        UserAccForm user = new UserAccForm();
        BeanUtils.copyProperties(entity, user);
        return user;
    }

    @Override
    public String unlockUserAccount(UnlockAccForm unlockAccForm) {
        UserEntity userEntity = userRepo.findByEmailAndPwd(unlockAccForm.getEmail(), unlockAccForm.getTempPwd());

        if(userEntity != null){
            userEntity.setPwd(unlockAccForm.getNewPwd());
            userEntity.setAccStatus("UNLOCKED");
            userRepo.save(userEntity);
            return "password changed successfully and now your account is unlocked. Please Login!!";
        }
        return "Invalid Credentials";
    }

    private String readEmailBody(String filename, UserEntity user){
        StringBuilder sb = new StringBuilder();
        try(Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                line = line.replace("${FNAME}", user.getFullName());
                line = line.replace("${PWD}", user.getPwd());
                line = line.replace("${EMAIL}", user.getEmail());
                sb.append(line);
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}

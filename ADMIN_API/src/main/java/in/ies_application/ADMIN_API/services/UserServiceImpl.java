package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.DashboardCard;
import in.ies_application.ADMIN_API.bindings.LoginForm;
import in.ies_application.ADMIN_API.bindings.UnlockAccForm;
import in.ies_application.ADMIN_API.bindings.UserAccForm;
import in.ies_application.ADMIN_API.constants.AppConstants;
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
        }else if((AppConstants.Y).equals(user.getActiveSw()) && (AppConstants.ACCOUNT_LOCKED_INACTIVE).equals(user.getAccStatus())){
            return AppConstants.SUCCESS;
        }else{
            return AppConstants.ACCOUNT_LOCKED_INACTIVE;
        }
    }

    @Override
    public boolean recoverPwd(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        if(userEntity != null){
            String subject = AppConstants.RECOVER_PWD_SUBJECT;
            String body = readEmailBody(AppConstants.FORGOT_PWD_EMAIL_BODY_FILE, userEntity);
            return emailUtils.sendEmail(subject, body, userEntity.getEmail());
        }
        return false;
    }

    @Override
    public DashboardCard fetchDashboardInfo() {
        long plansCnt = planRepo.count();

        List<EligEntity> eligList = eligRepo.findAll();

        Long approvedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.AP)).count();
        Long denielCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals(AppConstants.DP)).count();
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
            userEntity.setAccStatus(AppConstants.UNLOCKED);
            userRepo.save(userEntity);
            return AppConstants.PWD_CHANGED_SUCCESS;
        }
        return AppConstants.INVALID_CRED;
    }

    private String readEmailBody(String filename, UserEntity user){
        StringBuilder sb = new StringBuilder();
        try(Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                line = line.replace(AppConstants.FNAME, user.getFullName());
                line = line.replace(AppConstants.EMAIL, user.getPwd());
                line = line.replace(AppConstants.PWD, user.getEmail());
                sb.append(line);
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}

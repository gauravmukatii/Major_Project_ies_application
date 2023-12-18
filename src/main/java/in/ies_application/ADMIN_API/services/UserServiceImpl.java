package in.ies_application.ADMIN_API.services;

import in.ies_application.ADMIN_API.bindings.DashboardCard;
import in.ies_application.ADMIN_API.bindings.LoginForm;
import in.ies_application.ADMIN_API.bindings.UnlockAccForm;
import in.ies_application.ADMIN_API.entity.EligEntity;
import in.ies_application.ADMIN_API.entity.UserEntity;
import in.ies_application.ADMIN_API.repos.EligRepo;
import in.ies_application.ADMIN_API.repos.PlanRepo;
import in.ies_application.ADMIN_API.repos.UserRepo;
import in.ies_application.ADMIN_API.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        UserEntity user = userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPwd());

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
        UserEntity user = userRepo.findByEmail(email);
        if(userRepo.findByEmail(email) != null){
            String subject = "Your Password";
            String body = user.getPwd();
            return emailUtils.sendEmail(subject, body, user.getEmail());
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
    public String unlockUserAccount(UnlockAccForm unlockAccForm) {
        return null;
    }
}

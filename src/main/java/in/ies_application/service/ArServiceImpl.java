package in.ies_application.service;

import in.ies_application.binding.App;
import in.ies_application.entities.AppEntity;
import in.ies_application.entities.UserEntity;
import in.ies_application.exception.SsnWebException;
import in.ies_application.repos.AppRepo;
import in.ies_application.repos.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArServiceImpl implements ArService {
    @Autowired
    private AppRepo appRepo;
    @Autowired
    private UserRepo userRepo;
    private static final String SSA_WEB_API_URL = "http://localhost:8080/api/{ssn}";
    @Override
    public String createApplication(App app) {
        WebClient webClient = WebClient.create();
        try{
//            String stateName = webClient.get()
//                    .uri(SSA_WEB_API_URL, app.getSsn())
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
            String stateName = "RI";
            if("RI".equals(stateName)){
                UserEntity userEntity = userRepo.findById(app.getUserId()).get();
                AppEntity appEntity = new AppEntity();
                BeanUtils.copyProperties(app, appEntity);
                appEntity.setUser(userEntity);
                appEntity = appRepo.save(appEntity);
                return "App Created with case number : " + appEntity.getCaseNum();
            }
        }catch (Exception e){
            throw new SsnWebException(e.getMessage());
        }
        return "Invalid Ssn";
    }

    @Override
    public List<App> fetchApps(Integer userId) {

        UserEntity userEntity = userRepo.findById(userId).get();
        Integer roleId = userEntity.getRoleId();
        List<AppEntity> appEntities = null;

        if(1 == roleId){
            appEntities = appRepo.fetchUserApps();
        }else{
            appEntities = appRepo.fetchCwApps(userId);
        }
        List<App> apps = new ArrayList<>();

        for(AppEntity entity: appEntities){
            App app = new App();
            BeanUtils.copyProperties(entity, app);
            apps.add(app);
        }

        return null;
    }
}

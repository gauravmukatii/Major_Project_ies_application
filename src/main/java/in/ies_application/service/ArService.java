package in.ies_application.service;

import in.ies_application.binding.App;

import java.util.List;

public interface ArService {
    public String createApplication(App app);
    public List<App> fetchApps(Integer userId);
}

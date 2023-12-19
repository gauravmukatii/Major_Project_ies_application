package in.ies_application.ADMIN_API.bindings;

import lombok.Data;

@Data
public class DashboardCard {

    private Long plansCnt;
    private Long approveCnt;
    private Long denielCnt;
    private Double benefitAmtGiven;
    private UserAccForm user;
}

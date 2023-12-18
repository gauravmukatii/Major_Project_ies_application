package in.ies_application.ADMIN_API.bindings;

import lombok.Data;

@Data
public class DashboardCard {

    private Integer plansCnt;
    private Integer approveCnt;
    private Integer denielCnt;
    private Double benefitAmtGiven;
}

package com.center.medical.reservation.bean.param;

import lombok.Data;

/**
 * 新老系统集成预约接口-获取机构门店列表参数
 * @author xhp
 * @since 2024-01-04 8:20
 */
@Data
public class IntegratedReservationBranchListParam {
    //第三方套餐id
    private String bizComboId;
    //第三方系统id
    private String systemId;
}

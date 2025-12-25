package com.center.medical.reservation.bean.param;

import lombok.Data;

/**
 * 新老系统集成预约接口
 * 根据第三方订单ID获取预约状态参数
 * @author xhp
 * @since 2024-01-03 10:26
 */
@Data
public class IntegratedReservationStatusParam {
    //第三方订单id
    private String bizOrderNum;

    //第三方系统ID：本地传0
    private String systemId;
}

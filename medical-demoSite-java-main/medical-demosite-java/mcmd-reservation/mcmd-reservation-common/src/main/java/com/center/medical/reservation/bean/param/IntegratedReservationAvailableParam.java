package com.center.medical.reservation.bean.param;

import lombok.Data;

import java.util.Date;

/**
 * @author xhp
 * @since 2024-01-03 8:07
 */
@Data
public class IntegratedReservationAvailableParam {
    //机构门店ID 必填
    private String hospitalSubId;

    //开始日期 yyyy-MM-dd
    private String startDate;

    //结束日期 yyyy-MM-dd
    private String endDate;
}

package com.center.medical.reservation.constant;

/**
 * 新老系统集成预约常量
 * @author xhp
 * @since 2024-01-04 14:39
 */
public class IntegratedReservationConstant {
    //老系统根据第三方套餐id获取可预约机构门店列表
    public static final String OLD_SYS_GET_BRANCH_LIST_PATH="/inter/integrated_reservation!getBranchList.action";
    //老系统获取可预约时间段列表
    public static final String OLD_SYS_GET_AVAILABLE_NUM_PATH="/inter/integrated_reservation!getAvailableNums.action";
    //老系统预约取消
    public static final String OLD_STS_CANCEL_PATH="/inter/integrated_reservation!cancel.action";
    //老系统根据第三方订单ID获取预约状态
    public static final String OLD_SYS_STATUS_PATH="/inter/integrated_reservation!status.action";
    //老系统申请预约
    public static final String OLD_SYS_APPLY_PATH="/inter/integrated_reservation!apply.action";
}

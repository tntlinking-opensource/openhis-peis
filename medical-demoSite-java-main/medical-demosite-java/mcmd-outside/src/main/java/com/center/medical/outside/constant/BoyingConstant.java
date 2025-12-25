package com.center.medical.outside.constant;

/**
 * 博英心电信息管理系统
 * @author xhp
 * @since 2025-05-23 14:45
 */
public class BoyingConstant {

    public final static String SERVICE_URL="http://121.42.142.245:8085/WSInterface.asmx";
    public final static String SERVICE_NAMESPACE="http://tempuri.org/";

    /**
     * 获取患者信息
     */
    public final static String SERVICE_NAME_GET_PATIENT_INFO="GetPatientInfo";

    /**
     * 接收报告
     */
    public final static String SERVICE_NAME_WRITE_REPORT="WriteReport";

    /**
     * 重新触发报告回传
     */
    public final static String SERVICE_NAME_AGAIN_REPORT_BACK="AgainReportBack";

    /**
     * 重新触发报告回传接口地址
     */
    public final static String AGAIN_REPORT_BACK_URL="/open/api/boying/getResult";

    public final static String ATTACHMENT_MEMO="博英接口回传";

    //心电图科室编号
    public final static String DEPT_ID_ELECTROCARDIOGRAM="9";

    //检查结果缓存Key
    public final static String RESULT_KEY="BOYING_ELE_RESULT::";
    //检查结果缓存时间7天
    public static final long RESULT_EXPIRES_TIME = 60*60*24*5;
}

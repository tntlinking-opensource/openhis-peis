package com.center.medical.center.common.constant;

/**
 * 瑞林萨尔健康管理系统对接
 * @author xhp
 * @since 2025-03-21 15:27
 */
public class RilinConstant {
    //分页上传，每次上传数量
    public final static int PAGE_SIZE=100;
    //体检结果分批上传，每次上传数量
    public final static int PATIENT_SIZE=10;
    //错误信息最大长度
    public final static int ERROR_MSG_LIMIT=1800;

    public final static String SELLCUSTOMER="crm_sellcustomer";

    public final static String USER="sys_user";

    public final static String ITEMS="md_items";

    public final static String INSPECT_CHARGE="md_inspect_charge";

    public final static String BASEXAMLTEM="md_basexamltem";

    public final static String PATIENT="md_peispatient";

    public final static String RISK="md_riskclient";

    public final static String REMARK_FAILED="上传失败";

    public final static String ERROR_MSG_DUPLICATE_ENTRY="Duplicate entry";
}

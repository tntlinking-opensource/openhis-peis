package com.center.medical.workflow.bean.enums;

/**
 * 工作流类型
 *
 * @author 路飞
 */
public enum WorkflowType {
    ORDER_FLOW("ORDER_FLOW", "订单审核(健康)"),
    ORDER_FLOW_CHANGE("ORDER_FLOW_CHANGE", "订单变更审核(健康)"),
    ORDER_FLOW_CHANGE_OCCUPATIONAL("ORDER_FLOW_CHANGE_OCCUPATIONAL", "订单变更审核(职业)"),
    FEE_ITEM_FLOW("FEE_ITEM_FLOW", "收费项目审核"),
    CONCLUSION_FLOW("CONCLUSION_FLOW", "总检结论词审核"),
    OVER_RESERVATION("OVER_RESERVATION", "跨级预约审批"),
    EXCESS_RESERVATION("EXCESS_RESERVATION", "超额预约审批"),
    ORDER_FLOW_OCCUPATION("ORDER_FLOW_OCCUPATION", "订单审核(职业)"),;

    private final String code;
    private final String info;

    WorkflowType(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}

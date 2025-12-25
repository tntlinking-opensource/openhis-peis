package com.center.medical.report.bean.param;


import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 浮俊杰
 * @date: 2022-11-23 17:27
 * @description: 查询健康报告数据参数
 */
@Data
public class PeispatientParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 0;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "生成次数")
    private String createReportNum;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "已打印")
    private String isPrint;

    @ApiModelProperty(value = "未打印")
    private String isNotPrint;

    @ApiModelProperty(value = "登记时间起")
    private Date startTime;

    @ApiModelProperty(value = "登记时间止")
    private Date endTime;

    @ApiModelProperty(value = "客户单位名称id")
    private String khdwmcid;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "开单医生")
    private String idOpendoctor;

    @ApiModelProperty(value = "隐私报告")
    private String containsPrivate;

    @ApiModelProperty(value = "是否补全，true是false否")
    private String autoFill;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "展示未总检的 0不展示，1展示")
    private Integer showNotToatal;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "含老系统 0否1是")
    private Integer containOldSystem;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

}

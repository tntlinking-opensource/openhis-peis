package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: fjj
 * @date: 2022-11-23 17:27
 * @description: 查询职业团检样本参数
 */
@Data
public class ReportAuditParam implements Serializable {

    private static final long serialVersionUID = 0;
    
    @ApiModelProperty(value = "团检名称")
    private String orgName;

    @ApiModelProperty(value = "样本名称")
    private String sampleName;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer medicaltype;

    @ApiModelProperty(value = "状态")
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "用户ID,非参数")
    private String userNo;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String tjlx;


    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

}

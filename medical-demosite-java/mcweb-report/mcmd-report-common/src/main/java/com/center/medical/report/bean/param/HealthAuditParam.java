package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-11-23 17:27
 * @description: 查询报告列表数据参数
 */
@Data
public class HealthAuditParam implements Serializable {

    private static final long serialVersionUID = -2593824819798256165L;
    
    @ApiModelProperty(value = "是否补全")
    private String autoFill;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "任务ID")
    private String numorgresv;

    @ApiModelProperty(value = "0:总检开始、1:总检完成、2:报告已打印、" +
            "                     * 3:报告一审通过、4:报告一审不通过、" +
            "                     * 5:二审通过、6:二审不通过、" +
            "                     * 7:终审通过、8:终审不通过、" +
            "                     * 9:报告已交接、10:报告已通知、11:报告已领取")
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;


    @ApiModelProperty(value = "标识,没有筛选条件时，这个为1")
    private String flag;

}

package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: fjj
 * @date: 2022-11-23 17:27
 * @description: 查询职业团检样本参数
 */
@Data
public class GroupHealthParam implements Serializable {

    private static final long serialVersionUID = 0;
    
    @ApiModelProperty(value = "团检名称")
    private String orgName;

    @ApiModelProperty(value = "样本名称")
    private String sampleName;

    @ApiModelProperty(value = "健康职业标志 1:职业 0：健康")
    private Integer flag;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}

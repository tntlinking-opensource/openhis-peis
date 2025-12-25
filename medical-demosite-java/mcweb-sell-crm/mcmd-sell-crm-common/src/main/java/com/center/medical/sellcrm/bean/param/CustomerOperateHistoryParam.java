package com.center.medical.sellcrm.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerOperateHistoryParam implements Serializable {


    private static final long serialVersionUID = 5976048211854611844L;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "操作人")
    private String operateName;

    @ApiModelProperty(value = "销售经理名称")
    private String xsjlmc;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "分配人")
    private String fprmc;



}

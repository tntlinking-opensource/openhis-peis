package com.center.medical.member.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class TotalCountParam implements Serializable {
    private static final long serialVersionUID = -2435074836998278575L;

    @ApiModelProperty(value = "团体名称")
    private String orgName;


    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;
}

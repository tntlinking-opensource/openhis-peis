package com.center.medical.enterprise.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 体检报告 分页参数
 */
@Data
public class ReportListDataParam implements Serializable {

    private static final long serialVersionUID = 5310604057068835567L;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "部门")
    private String dept;

    @ApiModelProperty(value = "体检者名称")
    private String name;

    @ApiModelProperty(value = "订单号")
    private String dd;


    @ApiModelProperty(value = "选择数据的id")
    private List<String> ids;

}

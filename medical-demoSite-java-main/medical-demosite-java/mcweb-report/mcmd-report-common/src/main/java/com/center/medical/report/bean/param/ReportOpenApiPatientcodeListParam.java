package com.center.medical.report.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取体检号列表
 * @author xhp
 * @since 2023-10-26 7:51
 */
@Data
public class ReportOpenApiPatientcodeListParam implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间",example = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间",example = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "单位名称模糊查询")
    private String orgName;
}

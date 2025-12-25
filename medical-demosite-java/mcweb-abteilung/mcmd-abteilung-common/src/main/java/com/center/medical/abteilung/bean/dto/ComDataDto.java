package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 职业性问诊-审核保存参数
 */
@Data
public class ComDataDto implements Serializable {

    private static final long serialVersionUID = -7221033231772181814L;

    @ApiModelProperty(value = "状态 removed删除，modified修改，added添加")
    private String state;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "诊断日期")
    private Date diagnosisDate;

    @ApiModelProperty(value = "诊断单位")
    private String diagnosisDept;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "病名")
    private String occupationDiseast;

    @ApiModelProperty(value = "职业病名称")
    private String occupationDiseastName;
}

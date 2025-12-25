package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取接害信息
 */
@Data
public class TjRenMJieHai implements Serializable {
    private static final long serialVersionUID = 3726670116696647323L;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date stopDate;

    @ApiModelProperty(value = "工作单位")
    private String dept;

    @ApiModelProperty(value = "工种")
    private String typeOfWork;

    @ApiModelProperty(value = "毒害种类和名称")
    private String occupationHarm;

    @ApiModelProperty(value = "有无防护,0无，1有")
    private String occupationDefend;

    @ApiModelProperty(value = "录入人名称")
    private String lrr;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;
}

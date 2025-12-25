package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史数据返回数据
 */
@Data
public class PacsHistoryListVo implements Serializable {
    private static final long serialVersionUID = -3345895173722979928L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "检查结果描述")
    private String description;

    @ApiModelProperty(value = "检查结果总结")
    private String conclusions;

    @ApiModelProperty(value = "登记时间")
    private LocalDateTime dateregister;

    @ApiModelProperty(value = "收费项目名称")
    private String feeitemName;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "年龄")
    private int age;

    @ApiModelProperty(value = "名称")
    private String deptName;

    @ApiModelProperty(value = "性别")
    private String sex;


}

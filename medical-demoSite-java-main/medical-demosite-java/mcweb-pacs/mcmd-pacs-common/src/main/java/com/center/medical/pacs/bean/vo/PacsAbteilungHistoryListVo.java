package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xhp
 * @since 2023-03-22 10:01
 */
@Data
public class PacsAbteilungHistoryListVo {
    @ApiModelProperty(value = "体检号")
    private String patientcode;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "小结")
    private String conclusions;
    @ApiModelProperty(value = "登记时间")
    private LocalDateTime dateregister;
    @ApiModelProperty(value = "项目名称")
    private String feeitemName;
    @ApiModelProperty(value = "姓名")
    private String patientname;
    @ApiModelProperty(value = "性别 0男 1女 2通用")
    private String idSex;
    @ApiModelProperty(value = "年龄")
    private int age;
    @ApiModelProperty(value = "名称")
    private String deptName;
}

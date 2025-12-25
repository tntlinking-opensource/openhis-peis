package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成放射科室报告 参数
 */
@Data
public class CreateRadParam implements Serializable {
    private static final long serialVersionUID = 2973215428621972028L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室id")
    private String ksId;

    @ApiModelProperty(value = "1是检查部位、方法、技术：0是检查部位：")
    private Integer type;

    @ApiModelProperty(value = "技术、方法")
    private String tech;

    @ApiModelProperty(value = "是否打印图片,true是")
    private String addpic;

    @ApiModelProperty(value = "0新pacs系统,1体检软件生成彩超报告")
    private Integer source;
}

package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验样品-检验加项添加保存參數
 */
@Data
public class HNSOUParam implements Serializable {

    private static final long serialVersionUID = 6206539677554199344L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "项目名称")
    private String item;

    @ApiModelProperty(value = "状态，未处理0，处理中1，检验结束2，需要重检3")
    private String status;

    @ApiModelProperty(value = "处理人id")
    private String handleNameId;

    @ApiModelProperty(value = "处理时间")
    private Date handleTime;

    @ApiModelProperty(value = "处理意见")
    private String idea;

}

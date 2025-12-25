package com.center.medical.system.bean.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改健康或职业状态
 */
@Data
public class ModifyPeiStatusParam implements Serializable {
    private static final long serialVersionUID = -4892207977208814542L;


    @NotNull(message = "体检号不能为空")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer status;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "0.健康 1.职业")
    private Integer diseaseHealth;

}

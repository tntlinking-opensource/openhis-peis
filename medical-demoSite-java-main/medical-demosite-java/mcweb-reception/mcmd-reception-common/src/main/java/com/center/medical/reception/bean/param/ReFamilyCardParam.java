package com.center.medical.reception.bean.param;


import com.center.medical.bean.param.OldFamilyConParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭卡 退款
 */
@Data
public class ReFamilyCardParam implements Serializable {
    private static final long serialVersionUID = 4703223858693600675L;

    @ApiModelProperty(value = "类型,传saveCard")
    private String type;

    @ApiModelProperty(value = "版本")
    private Long version;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "formdata")
    private OldFamilyConParam formdata;

    @ApiModelProperty(value = "消费数据")
    private TJKDataParam data;
}

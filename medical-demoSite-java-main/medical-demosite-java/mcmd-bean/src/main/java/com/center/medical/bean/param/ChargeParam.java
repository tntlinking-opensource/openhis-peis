package com.center.medical.bean.param;

import com.center.medical.bean.model.Peispatientcharge;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-16 17:39
 * @description: 登记收费提交参数
 */
@Data
public class ChargeParam implements Serializable {
    private static final long serialVersionUID = 3015937502955441431L;

    @ApiModelProperty(value = "体检号(ID)")
    private String patientcode;

    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "ids")
    private String ids;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "是否通知检验科：0.否 1.是")
    private String key;

    @ApiModelProperty(value = "版本号")
    private Long version;

    @ApiModelProperty(value = "体检者缴费记录列表")
    private List<Peispatientcharge> chargeItems;

}

package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取分组下相应的人员 参数
 */
@Data
public class OrderPaDataParam implements Serializable {
    private static final long serialVersionUID = -5736914179424357090L;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private String fRegistered;

    @ApiModelProperty(value = "排序字段:{pinyin,patientcode,patientname,idSex,age}")
    private String sortField;

    @ApiModelProperty(value = "订单排序 asc正序或desc倒序")
    private String sortOrder;

    @ApiModelProperty(value = "姓名")
    private String patientname;

}

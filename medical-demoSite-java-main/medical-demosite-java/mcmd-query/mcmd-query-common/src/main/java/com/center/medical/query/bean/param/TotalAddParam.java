package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 加项情况查询 分页 参数
 */
@Data
public class TotalAddParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7479959968388870794L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientName;

    @ApiModelProperty(value = "登记人")
    private String registerR;

    @ApiModelProperty(value = "收费员")
    private String idFeecharger;

    @ApiModelProperty(value = "体检套餐名称")
    private String examname;

    @ApiModelProperty(value = "加项医师")
    private String jxys;

    @ApiModelProperty(value = "订单号")
    private String ddh;
    
    @ApiModelProperty(value = "开单医生ID")
    private String idOpendoctor;


}

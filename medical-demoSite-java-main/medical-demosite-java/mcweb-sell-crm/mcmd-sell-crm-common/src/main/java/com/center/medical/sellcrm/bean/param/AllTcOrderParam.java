package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取订单下的所有套餐参数
 */
@Data
public class AllTcOrderParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 7052026703949901581L;

    @ApiModelProperty(value = "订单id")
    private String idExamsuite;

    @ApiModelProperty(value = "体检套餐输入码")
    private String key;


}

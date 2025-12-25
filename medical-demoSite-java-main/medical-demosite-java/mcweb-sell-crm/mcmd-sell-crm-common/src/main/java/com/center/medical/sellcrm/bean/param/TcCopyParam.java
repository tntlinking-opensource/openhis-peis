package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-16 16:24
 * @description: 复制最小套餐参数
 */
@Data
public class TcCopyParam implements Serializable {
    private static final long serialVersionUID = -5758166647129962654L;

    @ApiModelProperty(value = "套餐id（选择复制的套餐）")
    private String tcId;

    @ApiModelProperty(value = "适用性别")
    private Integer syxb;

    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String zyValue;

    @ApiModelProperty(value = "接害因素id")
    private String jhId;


}

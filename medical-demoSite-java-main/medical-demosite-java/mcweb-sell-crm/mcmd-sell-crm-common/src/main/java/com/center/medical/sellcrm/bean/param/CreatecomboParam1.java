package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-11-15 10:51
 * @description: 最小套餐查询参数1
 */
@Data
public class CreatecomboParam1 implements Serializable {

    private static final long serialVersionUID = -7521180761350960253L;

    @ApiModelProperty(value = "收费项目ID集合，多个以英文逗号（,）隔开")
    private List<String> itemsIds;

    @ApiModelProperty(value = "接害因素id集合，多个以英文逗号（,）隔开")
    private List<String> jhId;

    @ApiModelProperty(value = "职业类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer zyValue;

    @ApiModelProperty(value = "是否必检：0.选检 1.必检")
    private Integer flag;

}

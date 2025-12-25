package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-15 18:56
 * @description: 套餐关联的收费项目查询参数
 */
@Data
public class ComboanditemParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5959276847875868961L;

    @ApiModelProperty(value = "套餐ID")
    private String comboId;

}

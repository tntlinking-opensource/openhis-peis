package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客服销售统计 分页 参数
 */
@Data
public class ICPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5456844927016782831L;
    

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "部门,1客服")
    private Integer depart;

}

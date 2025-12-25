package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 自费收费汇总 分页参数
 */
@Data
public class CCPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2100142848329440876L;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;


}

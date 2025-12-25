package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日疫苗收费统计 分页参数
 */
@Data
public class GetVaccinumParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5898715997342606489L;

    @ApiModelProperty(value = "销售经理名称")
    private String xsjl;


}

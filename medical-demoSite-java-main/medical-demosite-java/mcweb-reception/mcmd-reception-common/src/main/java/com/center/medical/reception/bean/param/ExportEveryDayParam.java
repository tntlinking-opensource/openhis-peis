package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出Excel(记账报表) 参数
 */
@Data
public class ExportEveryDayParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 632339776958230615L;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

}

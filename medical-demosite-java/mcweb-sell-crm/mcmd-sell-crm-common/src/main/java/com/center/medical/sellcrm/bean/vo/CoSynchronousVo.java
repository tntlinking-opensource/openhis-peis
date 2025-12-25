package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-16 17:58
 * @description: 最小套餐同步对象数据
 */
@Data
public class CoSynchronousVo implements Serializable {
    private static final long serialVersionUID = -5282275565147755059L;

    @ApiModelProperty(value = "套餐ID")
    private String comboId;

    @ApiModelProperty(value = "接害因素id")
    private String harmId;

    @ApiModelProperty(value = "职业体检类别，详见：com.center.medical.bean.enums.ZYTJLB")
    private String zytjlb;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;

    @ApiModelProperty(value = "检查项目ID")
    private String examId;

    @ApiModelProperty(value = "男取值范围上限")
    private Double scopeUpper;

    @ApiModelProperty(value = "男取值范围下限")
    private Double scoperFloor;

    @ApiModelProperty(value = "女取值范围上限")
    private Double gscopeUpper;

    @ApiModelProperty(value = "女取值范围下限")
    private Double gscoperFloor;
}

package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售年度目标保存参数
 */
@Data
public class LTSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -7932744955963179217L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "全年目标,就是target")
    private Double ndmb;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "用户id，实际就是userid")
    private String leaUserId;
}

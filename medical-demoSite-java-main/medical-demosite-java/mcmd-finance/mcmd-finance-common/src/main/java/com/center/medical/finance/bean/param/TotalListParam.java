package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售团检统计 查询右边列表 参数
 */
@Data
public class TotalListParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -443046228502441488L;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "单位数字ID")
    private String id;

    @ApiModelProperty(value = "体检类型：0:健康检查类型 1:职业检查类型 2:健康+职业(职业)")
    private Integer type;

}

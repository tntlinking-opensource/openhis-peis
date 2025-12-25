package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 体检结账单数据分页参数
 */
@Data
public class AnalyseParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2648391798198261433L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "订单号")
    private List<String> order;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private Integer intId;


    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

}

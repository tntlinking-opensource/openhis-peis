package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-28 9:08
 * @description: 签单计划查询参数
 */
@Data
public class OrderPlanParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 4947089063361056797L;

    @ApiModelProperty(value = "签单计划名称")
    private String ddmc;

    @ApiModelProperty(value = "单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "年度")
    private String yearFlag;

    @ApiModelProperty(value = "业务员ID")
    private String salesId;

    @ApiModelProperty(value = "文件状态：-1.失效 0.待提交 1.撞单处理中 2.待审核 3.审核通过 4.审核驳回")
    private Integer status;

}

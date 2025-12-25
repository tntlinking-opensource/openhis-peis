package com.center.medical.report.bean.param;

import com.center.medical.bean.model.SysBranch;
import com.center.medical.report.bean.vo.ReportConfigVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 综合分析-参数
 */
@Data
public class CReprotNewDParam implements Serializable {
    private static final long serialVersionUID = -8964838508612537945L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "健康0 职业1")
    private String dh;

    @ApiModelProperty(value = "样本Id")
    private String analyzeId;

    @ApiModelProperty(value = "用户名,没用到")
    private String username;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "结论词序列号")
    private Integer summarySerialNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "报告模板地址")
    private String[] modelUrls;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "地址前缀")
    private String prefix;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "报告配置")
    private ReportConfigVo reportConfigVo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心设置")
    private SysBranch sysBranch;


    @ApiParam(hidden = true)
    @ApiModelProperty(value = "报告生成人")
    private String generateName;

}

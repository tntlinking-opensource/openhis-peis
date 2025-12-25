package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取右侧详情返回数据
 */
@Data
public class InfoListDataVo implements Serializable {
    private static final long serialVersionUID = 5969246572224727010L;

    @ApiModelProperty(value = "打印名称")
    private String examitemNameprn;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "LIS样本编号")
    private Double lisybbh;

}

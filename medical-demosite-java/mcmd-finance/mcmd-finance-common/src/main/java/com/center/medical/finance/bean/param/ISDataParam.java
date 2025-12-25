package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 获取统计数据参数
 */
@Data
public class ISDataParam implements Serializable {
    private static final long serialVersionUID = 3972922132429100414L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心名字集合")
    private List<String> branchNameList;


    @ApiModelProperty(value = "类型 1产值、2利润情况、3现金流、4产值分布、5客户情况、" +
            "6来检人数情况、7销售数据情况、8费用占比、9人均情况")
    private Integer type;

}

package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 项目明细统计 分页参数
 */
@Data
public class ItemsDetailsStatisticsParam implements Serializable {
    private static final long serialVersionUID = -1358649820961905485L;

    @ApiModelProperty(value = "分中心ids,传参时多个则以英文逗号（,）隔开")
    private List<String> branchIds;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "项目名称")
    private String examfeeitemName;

}

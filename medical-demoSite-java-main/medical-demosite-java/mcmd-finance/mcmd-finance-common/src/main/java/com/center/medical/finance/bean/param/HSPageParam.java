package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 销售团检统计 分页 参数
 */
@Data
public class HSPageParam implements Serializable {
    private static final long serialVersionUID = -7904531944324869708L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "单位名称id")
    private String idOrg;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

}

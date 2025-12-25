package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分享报告 返回数据
 */
@Data
public class ReportShareMainVo implements Serializable {
    private static final long serialVersionUID = -2603465658118660228L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "链接名称")
    private String pathName;

    @ApiModelProperty(value = "分享链接")
    private String path;

    @ApiModelProperty(value = "提取码")
    private String extractedCode;

    @ApiModelProperty(value = "已选人数")
    private Integer num;

    @ApiModelProperty(value = "有效期 7,14,30,999")
    private String expirationDate;

    @ApiModelProperty(value = "自动填充分享码 0否1是")
    private Integer autofill;

    @ApiModelProperty(value = "状态 0生效1过期")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;


}

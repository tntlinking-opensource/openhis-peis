package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 检查数据返回数据
 */
@Data
public class CheckDateVo implements Serializable {
    private static final long serialVersionUID = -8030450090000551042L;

    @ApiModelProperty(value = "分中心集合")
    private String fzxs;

    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "男性体检人数")
    private Integer nxtjrs;

    @ApiModelProperty(value = "女性体检人数")
    private Integer vxtjrs;


}

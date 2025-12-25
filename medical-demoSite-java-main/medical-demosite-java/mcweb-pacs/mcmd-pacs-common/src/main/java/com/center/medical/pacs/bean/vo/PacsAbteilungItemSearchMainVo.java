package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xhp
 * @since 2023-03-22 10:26
 */
@Data
public class PacsAbteilungItemSearchMainVo {
    @ApiModelProperty(value = "小结")
    private String conclusions;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "检查人用户名")
    private String rummager;
    @ApiModelProperty(value = "检查人用户编号")
    private String rummagerId;
    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;
    @ApiModelProperty(value = "审核人用户名")
    private String writer;
    @ApiModelProperty(value = "审核人用户编号")
    private String writeId;
    @ApiModelProperty(value = "审核时间")
    private Date writeTime;
    @ApiModelProperty(value = "0未检  1已检")
    private int isAudit;
}

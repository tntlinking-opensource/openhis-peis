package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 批量职业健康检查复查通知书(MdGroupReviewNotice)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Data
@TableName("md_group_review_notice")
@ApiModel(value = "MdGroupReviewNotice", description = "批量职业健康检查复查通知书实体类")
public class MdGroupReviewNotice extends Model<MdGroupReviewNotice> implements Serializable {
    private static final long serialVersionUID = -80999384219431686L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "创建者")
    private String creator;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "配置id")
    private String configId;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "ddh")
    private String ddh;
}

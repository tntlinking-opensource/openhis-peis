package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 批量职业健康检查复查通知书(GroupReviewNotice)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_group_review_notice")
@ApiModel(value = "GroupReviewNotice", description = "批量职业健康检查复查通知书实体类")
public class GroupReviewNotice extends Model<GroupReviewNotice> implements Serializable {
    private static final long serialVersionUID = 734071848380852007L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
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

    @ApiModelProperty(value = "配置id(改成分中心id吧)")
    private String configId;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "ddh")
    private String ddh;
}

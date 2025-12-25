package com.center.medical.datamove.oracle.bean.model;


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
 * 批量职业健康检查复查通知书(GroupReviewNotice)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:06
 */
@Data
@TableName("GROUP_REVIEW_NOTICE")
@ApiModel(value = "GroupReviewNotice", description = "批量职业健康检查复查通知书实体类")
public class GroupReviewNotice extends Model<GroupReviewNotice> implements Serializable {
    private static final long serialVersionUID = 383757750166638227L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date startDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date endDate;

    @ApiModelProperty(value = "${column.comment}")
    private String customerId;

    @ApiModelProperty(value = "${column.comment}")
    private String creator;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private String errorMsg;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;

    @ApiModelProperty(value = "${column.comment}")
    private String url;

    @ApiModelProperty(value = "${column.comment}")
    private String ddh;
}

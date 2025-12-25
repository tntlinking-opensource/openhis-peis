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
 * 批量复查通知人员(GroupReviewNoticePatient)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:08
 */
@Data
@TableName("GROUP_REVIEW_NOTICE_PATIENT")
@ApiModel(value = "GroupReviewNoticePatient", description = "批量复查通知人员实体类")
public class GroupReviewNoticePatient extends Model<GroupReviewNoticePatient> implements Serializable {
    private static final long serialVersionUID = -83426010932439593L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String mainId;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;
}

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
 * 批量复查通知人员(MdGroupReviewNoticePatient)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
@Data
@TableName("md_group_review_notice_patient")
@ApiModel(value = "MdGroupReviewNoticePatient", description = "批量复查通知人员实体类")
public class MdGroupReviewNoticePatient extends Model<MdGroupReviewNoticePatient> implements Serializable {
    private static final long serialVersionUID = -44157592122328318L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "人员id")
    private String mainId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}

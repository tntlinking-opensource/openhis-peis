package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 批量复查通知人员(GroupReviewNoticePatient)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_group_review_notice_patient")
@ApiModel(value = "GroupReviewNoticePatient", description = "批量复查通知人员实体类")
public class GroupReviewNoticePatient extends Model<GroupReviewNoticePatient> implements Serializable {
    private static final long serialVersionUID = 953111134639643179L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "人员id")
    private String mainId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


    public GroupReviewNoticePatient(String mainId, String patientcode) {
        this.mainId = mainId;
        this.patientcode = patientcode;
    }


    public GroupReviewNoticePatient() {
    }
}

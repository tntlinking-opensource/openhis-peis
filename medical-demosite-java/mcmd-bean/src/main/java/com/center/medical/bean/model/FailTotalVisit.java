package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KF迟捡、阳性、不合格样本回访(FailTotalVisit)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_fail_total_visit")
@ApiModel(value = "FailTotalVisit", description = "KF迟捡、阳性、不合格样本回访实体类")
public class FailTotalVisit extends Model<FailTotalVisit> implements Serializable {
    private static final long serialVersionUID = 484145301034112692L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "回访类型")
    private Integer visitType;

    @ApiModelProperty(value = "体检号")
    private String personcode;

    @ApiModelProperty(value = "回访人ID")
    private String visitId;

    @ApiModelProperty(value = "回访备注")
    private String memo;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "迟捡回访人员表ID|阳性结果回访riskclientId(弃用)")
    private String visitMainId;

    @ApiModelProperty(value = "处理结果")
    private Integer sflj;

    @ApiModelProperty(value = "来检时间")
    private Date ljsj;

    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @ApiModelProperty(value = "跟进结果")
    private Integer visitResult;

    @ApiModelProperty(value = "再通知")
    private Integer noticeAgain;

    @ApiModelProperty(value = "预处理时间")
    private Date preTime;

    @ApiModelProperty(value = "预处理结果")
    private Integer preResult;
}

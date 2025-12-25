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
 * KF满意度(MdSatisfaction)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:27
 */
@Data
@TableName("md_satisfaction")
@ApiModel(value = "MdSatisfaction", description = "KF满意度实体类")
public class MdSatisfaction extends Model<MdSatisfaction> implements Serializable {
    private static final long serialVersionUID = -38730353748570130L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "0科室/1整个分中心/2前台评价/3咨询")
    private Integer divisionReceptionist;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "开单医生ID")
    private String doctorId;

    @ApiModelProperty(value = "，详见：com.world.center.bean.enums.AppraiseResult")
    private String appraiseResult;

    @ApiModelProperty(value = "回访结果")
    private Integer visitResult;

    @ApiModelProperty(value = "评价时间")
    private Date appraiseTime;

    @ApiModelProperty(value = "体检号")
    private String personcode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否已非常满意")
    private Integer isVerySatisfy;

    @ApiModelProperty(value = "评价备注")
    private String note;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "回访人")
    private String visitPerson;

    @ApiModelProperty(value = "回访备注")
    private String visitNote;

    @ApiModelProperty(value = "回访结果2")
    private Integer secondResult;

    @ApiModelProperty(value = "回访备注2")
    private String secondNote;

    @ApiModelProperty(value = "回访人2")
    private String secondPerson;

    @ApiModelProperty(value = "回访时间2")
    private Date secondTime;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @ApiModelProperty(value = "科室医生ID")
    private String ksDoctorId;

    @ApiModelProperty(value = "星级")
    private Integer satisfactionLevel;

    @ApiModelProperty(value = "评论来源  1微信小程序")
    private Integer sourceType;

    @ApiModelProperty(value = "微信小程序id")
    private String userId;

    @ApiModelProperty(value = "consult_id")
    private String consultId;
}

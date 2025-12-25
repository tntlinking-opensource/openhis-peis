package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KF满意度(Satisfaction)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:11
 */
@Data
@TableName("md_satisfaction")
@ApiModel(value = "Satisfaction", description = "KF满意度实体类")
public class Satisfaction extends Model<Satisfaction> implements Serializable {
    private static final long serialVersionUID = -32509427004751936L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "0科室/1整个分中心/2前台评价/3咨询")
    @ApiModelProperty(value = "0科室/1整个分中心/2前台评价/3咨询")
    private Integer divisionReceptionist;

    @Excel(name = "科室ID")
    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @Excel(name = "开单医生ID")
    @ApiModelProperty(value = "开单医生ID")
    private String doctorId;

    @Excel(name = "appraiseResult")
    @ApiModelProperty(value = "，详见：AppraiseResult")
    private String appraiseResult;

    @Excel(name = "回访结果")
    @ApiModelProperty(value = "回访结果")
    private Integer visitResult;

    @Excel(name = "评价时间")
    @ApiModelProperty(value = "评价时间")
    private Date appraiseTime;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String personcode;

    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "更新时间")
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @Excel(name = "是否已非常满意")
    @ApiModelProperty(value = "是否已非常满意")
    private Integer isVerySatisfy;

    @Excel(name = "评价备注")
    @ApiModelProperty(value = "评价备注")
    private String note;

    @Excel(name = "回访时间")
    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @Excel(name = "回访人")
    @ApiModelProperty(value = "回访人")
    private String visitPerson;

    @Excel(name = "回访备注")
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

    @Excel(name = "体检者姓名")
    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @Excel(name = "跟进内容")
    @ApiModelProperty(value = "跟进内容")
    private String visitText;

    @Excel(name = "科室医生ID")
    @ApiModelProperty(value = "科室医生ID")
    private String ksDoctorId;

    @Excel(name = "星级")
    @ApiModelProperty(value = "星级")
    private Integer satisfactionLevel;

    @ApiModelProperty(value = "评论来源  1微信小程序")
    private Integer sourceType;

    @ApiModelProperty(value = "微信小程序id")
    private String userId;

    @ApiModelProperty(value = "consult_id")
    private String consultId;

    @TableField(exist = false)
    @ApiModelProperty(value = "评价科室名称（这个字段就是原本的divisionId）")
    private String divisionIdName;


    @TableField(exist = false)
    @ApiModelProperty(value = "登记员名称（这个字段就是原本的ksDoctor）")
    private String ksDoctorName;

    @TableField(exist = false)
    @ApiModelProperty(value = "性别ID")
    private String idSex;


    @TableField(exist = false)
    @ApiModelProperty(value = "年龄")
    private String age;

    @TableField(exist = false)
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @TableField(exist = false)
    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @TableField(exist = false)
    @ApiModelProperty(value = "电话")
    private String phone;

    @TableField(exist = false)
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;
}

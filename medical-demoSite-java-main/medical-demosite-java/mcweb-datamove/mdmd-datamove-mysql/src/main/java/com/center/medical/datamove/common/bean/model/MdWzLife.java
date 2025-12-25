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
 * KS问诊——个人生活史(MdWzLife)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
@Data
@TableName("md_wz_life")
@ApiModel(value = "MdWzLife", description = "KS问诊——个人生活史实体类")
public class MdWzLife extends Model<MdWzLife> implements Serializable {
    private static final long serialVersionUID = -29082425502635842L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

    @ApiModelProperty(value = "序号(不用，是原带的）")
    private Integer id1;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "长期生活地区")
    private String cqshdq;

    @ApiModelProperty(value = "饮食习惯")
    private String ysxg;

    @ApiModelProperty(value = "起居习惯")
    private String qjxg;

    @ApiModelProperty(value = "性格特征")
    private String xgtz;

    @ApiModelProperty(value = "精神状况")
    private String jszk;

    @ApiModelProperty(value = "地方病")
    private String dfb;

    @ApiModelProperty(value = "疫区生活史")
    private String yqshs;

    @ApiModelProperty(value = "药物滥用情况")
    private String ywlyqk;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;
}

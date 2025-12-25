package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 本次职业健康检查漏检人员及漏检项目人员一览表(FxCompletion)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:47
 */
@Data
@TableName("FX_COMPLETION")
@ApiModel(value = "FxCompletion", description = "本次职业健康检查漏检人员及漏检项目人员一览表实体类")
public class FxCompletion extends Model<FxCompletion> implements Serializable {
    private static final long serialVersionUID = -47731497821749483L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private String sampleId;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private Double age;

    @ApiModelProperty(value = "接害工龄")
    private String jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "接害因素")
    private String jhysIds;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "${column.comment}")
    private String marriage;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "${column.comment}")
    private String uncheckedItems;

    @ApiModelProperty(value = "${column.comment}")
    private String uncheckedItemids;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "已开始体检")
    private Integer fExamstarted;

    @ApiModelProperty(value = "分检完成")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private String zytjzt;

    @ApiModelProperty(value = "${column.comment}")
    private String trades;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregister;

    @ApiModelProperty(value = "报告是否已打印")
    private Integer isPrint;

    @ApiModelProperty(value = "健康体检状态")
    private String jktjzt;

    @ApiModelProperty(value = "已登记 0：未登记；1：已登记")
    private Integer registered;

    @ApiModelProperty(value = "已登记 0：未登记；1：已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "${column.comment}")
    private Date dateregisternotime;

    @ApiModelProperty(value = "${column.comment}")
    private String idcardno;
}

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
 * 本次职业健康检查危害因素人员检查情况汇总一览表(FxSummary)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:00
 */
@Data
@TableName("FX_SUMMARY")
@ApiModel(value = "FxSummary", description = "本次职业健康检查危害因素人员检查情况汇总一览表实体类")
public class FxSummary extends Model<FxSummary> implements Serializable {
    private static final long serialVersionUID = 956231054449505107L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "职业体检类别(工作状态名称)")
    private String regimentationNote;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素ID")
    private String harmId;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "疑似职业病人数")
    private String yszyb;

    @ApiModelProperty(value = "职业禁忌症人数")
    private String zyjjz;

    @ApiModelProperty(value = "复查人数")
    private String fc;

    @ApiModelProperty(value = "其他疾病或异常人数")
    private String qtjb;

    @ApiModelProperty(value = "目前未见异常人数")
    private String wjyc;

    @ApiModelProperty(value = "危害因素种类ID")
    private String classId;

    @ApiModelProperty(value = "危害因素种类名称")
    private String className;
}

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
 * 高危人员关联表(MdRiskclientcon)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
@Data
@TableName("md_riskclientcon")
@ApiModel(value = "MdRiskclientcon", description = "高危人员关联表实体类")
public class MdRiskclientcon extends Model<MdRiskclientcon> implements Serializable {
    private static final long serialVersionUID = 800077470780255183L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "高危人员表ID")
    private String riskid;

    @ApiModelProperty(value = "高危项目")
    private String gwxm;

    @ApiModelProperty(value = "危机值小结")
    private String wjzxj;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "科室id")
    private String divisionId;

    @ApiModelProperty(value = "危急值级别")
    private Integer wjzjb;

    @ApiModelProperty(value = "医生名称")
    private String doctorId;

    @ApiModelProperty(value = "检查时间")
    private Date checkTime;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxm;

    @ApiModelProperty(value = "同步标志：1已同步 ")
    private Integer tbbz;
}

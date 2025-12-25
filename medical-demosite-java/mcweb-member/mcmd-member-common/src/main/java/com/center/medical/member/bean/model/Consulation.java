package com.center.medical.member.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室/总检咨询(Consulation)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:57
 */
@Data
@TableName("md_consulation")
@ApiModel(value = "Consulation", description = "科室/总检咨询实体类")
public class Consulation extends Model<Consulation> implements Serializable {
    private static final long serialVersionUID = 560576148496667686L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "咨询类型，1.现场咨询 2.来电咨询 3.电话回访")
    private Integer consultType;

    @ApiModelProperty(value = "咨询人姓名")
    private String consultName;

    @ApiModelProperty(value = "咨询时间")
    private Date consultTime;

    @ApiModelProperty(value = "科室id")
    private String depId;

    @ApiModelProperty(value = "签名图路径")
    private String signPath;

    @ApiModelProperty(value = "咨询内容")
    private String consultContent;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "咨询电话")
    private String consultPhone;

    @ApiModelProperty(value = "医生用户名")
    private String doctorUsername;

    @ApiModelProperty(value = "configid（attachmentconfig中的id）")
    private String configId;

}

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
 * 科室/总检咨询(MdConsulation)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
@Data
@TableName("md_consulation")
@ApiModel(value = "MdConsulation", description = "科室/总检咨询实体类")
public class MdConsulation extends Model<MdConsulation> implements Serializable {
    private static final long serialVersionUID = -75715492842785647L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "更新世家")
    private Date modifydate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "咨询类型，详见：com.world.center.bean.enums.ConsultType")
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

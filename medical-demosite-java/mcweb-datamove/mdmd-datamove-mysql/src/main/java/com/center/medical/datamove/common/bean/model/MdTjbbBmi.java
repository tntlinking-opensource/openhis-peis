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
 * KS体重指数体检报表(MdTjbbBmi)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:59
 */
@Data
@TableName("md_tjbb_bmi")
@ApiModel(value = "MdTjbbBmi", description = "KS体重指数体检报表实体类")
public class MdTjbbBmi extends Model<MdTjbbBmi> implements Serializable {
    private static final long serialVersionUID = 976233580474843785L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "BMI描述")
    private String bmi;

    @ApiModelProperty(value = "参考范围描述")
    private String ckfw;

    @ApiModelProperty(value = "低值")
    private Double valueMin;

    @ApiModelProperty(value = "高值")
    private Double valueMax;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}

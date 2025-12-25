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
 * KS锻炼情况(Dlqk)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:18
 */
@Data
@TableName("DLQK")
@ApiModel(value = "Dlqk", description = "KS锻炼情况实体类")
public class Dlqk extends Model<Dlqk> implements Serializable {
    private static final long serialVersionUID = -51321930662597862L;

    @ApiModelProperty(value = "编码")
    private String bm;

    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;
}

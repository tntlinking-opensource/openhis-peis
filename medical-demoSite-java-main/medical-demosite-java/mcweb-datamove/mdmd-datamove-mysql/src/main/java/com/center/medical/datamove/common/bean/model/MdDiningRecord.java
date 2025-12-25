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
 * 就餐记录(MdDiningRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
@Data
@TableName("md_dining_record")
@ApiModel(value = "MdDiningRecord", description = "就餐记录实体类")
public class MdDiningRecord extends Model<MdDiningRecord> implements Serializable {
    private static final long serialVersionUID = -13414974577387310L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "就餐时间")
    private Date diningTime;
}

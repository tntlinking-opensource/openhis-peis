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
 * 体检号生成策略(Peispatientsccl)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:32
 */
@Data
@TableName("PEISPATIENTSCCL")
@ApiModel(value = "Peispatientsccl", description = "体检号生成策略实体类")
public class Peispatientsccl extends Model<Peispatientsccl> implements Serializable {
    private static final long serialVersionUID = -15655132244465850L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "策略名称")
    private String clname;

    @ApiModelProperty(value = "分中心ID")
    private String idFzx;

    @ApiModelProperty(value = "策略内容")
    private String clcontent;

    @ApiModelProperty(value = "简码")
    private String jm;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

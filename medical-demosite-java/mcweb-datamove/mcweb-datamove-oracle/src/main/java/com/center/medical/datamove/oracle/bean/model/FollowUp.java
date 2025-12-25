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
 * JC随访目的维护(FollowUp)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:45
 */
@Data
@TableName("FOLLOW_UP")
@ApiModel(value = "FollowUp", description = "JC随访目的维护实体类")
public class FollowUp extends Model<FollowUp> implements Serializable {
    private static final long serialVersionUID = 904612184542647049L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "随访代码")
    private String followUpCode;

    @ApiModelProperty(value = "随访目的名称")
    private String followUpName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

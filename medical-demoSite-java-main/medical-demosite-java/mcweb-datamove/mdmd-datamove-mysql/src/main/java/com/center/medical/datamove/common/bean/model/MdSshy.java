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
 * 创建团体客户要选择的所属行业在这里维护(MdSshy)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:46
 */
@Data
@TableName("md_sshy")
@ApiModel(value = "MdSshy", description = "创建团体客户要选择的所属行业在这里维护实体类")
public class MdSshy extends Model<MdSshy> implements Serializable {
    private static final long serialVersionUID = 406449630719126063L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "行业名称")
    private String hymc;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

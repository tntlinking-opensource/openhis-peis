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
 * 快递公司表(MdExpresscompany)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
@Data
@TableName("md_expresscompany")
@ApiModel(value = "MdExpresscompany", description = "快递公司表实体类")
public class MdExpresscompany extends Model<MdExpresscompany> implements Serializable {
    private static final long serialVersionUID = 878460223038684222L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "通知方式")
    private String expressName;

    @ApiModelProperty(value = "联系方式")
    private String expressPhone;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

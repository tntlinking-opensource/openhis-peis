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
 * 上下级关系管理表(Upperower)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:25:41
 */
@Data
@TableName("UPPEROWER")
@ApiModel(value = "Upperower", description = "上下级关系管理表实体类")
public class Upperower extends Model<Upperower> implements Serializable {
    private static final long serialVersionUID = 820928518196522810L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "人员名称")
    private String rymc;

    @ApiModelProperty(value = "职位")
    private String zw;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "年龄")
    private String nl;

    @ApiModelProperty(value = "电话")
    private String dh;

    @ApiModelProperty(value = "是否有上级")
    private String sfysj;

    @ApiModelProperty(value = "上级")
    private String sj;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}

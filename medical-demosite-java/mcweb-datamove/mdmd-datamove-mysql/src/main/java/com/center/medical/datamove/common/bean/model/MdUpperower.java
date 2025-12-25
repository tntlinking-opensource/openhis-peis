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
 * 上下级关系管理表(MdUpperower)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:49:14
 */
@Data
@TableName("md_upperower")
@ApiModel(value = "MdUpperower", description = "上下级关系管理表实体类")
public class MdUpperower extends Model<MdUpperower> implements Serializable {
    private static final long serialVersionUID = 283885526883748833L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

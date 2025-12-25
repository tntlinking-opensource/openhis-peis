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
 * 外送机构(MdWsjg)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:21
 */
@Data
@TableName("md_wsjg")
@ApiModel(value = "MdWsjg", description = "外送机构实体类")
public class MdWsjg extends Model<MdWsjg> implements Serializable {
    private static final long serialVersionUID = 643547862937140686L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

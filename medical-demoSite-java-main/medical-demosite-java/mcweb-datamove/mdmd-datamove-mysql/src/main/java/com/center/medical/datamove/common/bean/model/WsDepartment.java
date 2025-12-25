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
 * 网站部门(WsDepartment)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Data
@TableName("ws_department")
@ApiModel(value = "WsDepartment", description = "网站部门实体类")
public class WsDepartment extends Model<WsDepartment> implements Serializable {
    private static final long serialVersionUID = 368574196890890192L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "父级Id")
    private String parent;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0未删除 1删除")
    private Integer isDelete;

    @ApiModelProperty(value = "（删）")
    private Integer isFunction;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "（删）")
    private Integer sjbggs;

    @ApiModelProperty(value = "（删）")
    private String jcdd;

    @ApiModelProperty(value = "（删）")
    private String jklx;

    @ApiModelProperty(value = "（删）")
    private String ksh;

    @ApiModelProperty(value = "（删）")
    private String imgpath;

    @ApiModelProperty(value = "（删）")
    private String reportPathHealth;

    @ApiModelProperty(value = "（删）")
    private String reportPathDisease;

    @ApiModelProperty(value = "（删）")
    private Integer addPicBefore;

    @ApiModelProperty(value = "（删）")
    private Integer reportSort;

    @ApiModelProperty(value = "（删）")
    private Integer dydXh;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "house")
    private String house;

    @ApiModelProperty(value = "payment")
    private String payment;

    @ApiModelProperty(value = "到期时间")
    private Date enddate;

    @ApiModelProperty(value = "标签")
    private String flag;

    @ApiModelProperty(value = "标签id")
    private String flagId;

    @ApiModelProperty(value = "组织编码")
    private String dpcode;

    @ApiModelProperty(value = "层级")
    private String dplevel;

    @ApiModelProperty(value = "客户ID")
    private String customerId;
}

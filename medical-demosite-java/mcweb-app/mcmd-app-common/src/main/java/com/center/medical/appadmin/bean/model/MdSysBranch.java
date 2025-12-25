package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分中心维护表(MdSysBranch)表实体类
 *
 * @author ay
 * @since 2024-06-11 16:03:16
 */
@Data
@TableName("md_sys_branch")
@ApiModel(value = "MdSysBranch", description = "分中心维护表实体类")
public class MdSysBranch extends Model<MdSysBranch> implements Serializable {
    private static final long serialVersionUID = -26639597445418244L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Integer id;


    @ApiModelProperty(value = "分中心id")
    private String branchId;


    @ApiModelProperty(value = "分中心名字")
    private String fzx;


    @ApiModelProperty(value = "简码，不能重复")
    private String jm;


    @ApiModelProperty(value = "父级分中心id")
    private String pid;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;


    @ApiModelProperty(value = "地址")
    private String address;


    @ApiModelProperty(value = "机构类型，详见：com.world.center.bean.enums")
    private Integer branchType;


    @ApiModelProperty(value = "经度")
    private String lng;


    @ApiModelProperty(value = "纬度")
    private String lat;


    @ApiModelProperty(value = "联系电话")
    private String tel;


    @ApiModelProperty(value = "省")
    private String province;


    @ApiModelProperty(value = "市")
    private String city;


    @ApiModelProperty(value = "区")
    private String area;


    @ApiModelProperty(value = "检前须知")
    private String inspectInfo;


    @ApiModelProperty(value = "机构照片")
    private String pics;


    @ApiModelProperty(value = "机构介绍")
    private String introduce;


    @ApiModelProperty(value = "前台须知")
    private String qtInfo;


    @ApiModelProperty(value = "缩略图")
    private String picture;


    @ApiModelProperty(value = "是否展示：0.否 1.是")
    private Integer isShow;


    @ApiModelProperty(value = "是否需要预约：0.否 1.是")
    private Integer needReservation;


    @ApiModelProperty(value = "排序")
    private Integer branchSort;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}

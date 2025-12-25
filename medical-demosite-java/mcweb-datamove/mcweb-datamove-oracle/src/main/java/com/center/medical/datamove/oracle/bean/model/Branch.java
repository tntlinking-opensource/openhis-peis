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
 * 分中心维护表(Branch)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:52
 */
@Data
@TableName("BRANCH")
@ApiModel(value = "Branch", description = "分中心维护表实体类")
public class Branch extends Model<Branch> implements Serializable {
    private static final long serialVersionUID = 904338784514099398L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;

    @ApiModelProperty(value = "简码不能重复(eq为假)")
    private String jm;

    @ApiModelProperty(value = "${column.comment}")
    private String srm;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "父级分中心id")
    private String pid;

    @ApiModelProperty(value = "0未删除1已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDefault;

    @ApiModelProperty(value = "${column.comment}")
    private String branchType;

    @ApiModelProperty(value = "${column.comment}")
    private Integer fPayOnline;

    @ApiModelProperty(value = "${column.comment}")
    private String lng;

    @ApiModelProperty(value = "${column.comment}")
    private String lat;

    @ApiModelProperty(value = "${column.comment}")
    private String tel;

    @ApiModelProperty(value = "${column.comment}")
    private String startTime;

    @ApiModelProperty(value = "${column.comment}")
    private String endTime;

    @ApiModelProperty(value = "${column.comment}")
    private String province;

    @ApiModelProperty(value = "${column.comment}")
    private String city;

    @ApiModelProperty(value = "${column.comment}")
    private String area;

    @ApiModelProperty(value = "${column.comment}")
    private String inspectInfo;

    @ApiModelProperty(value = "${column.comment}")
    private String noInfo;

    @ApiModelProperty(value = "${column.comment}")
    private String pics;

    @ApiModelProperty(value = "${column.comment}")
    private String introduce;

    @ApiModelProperty(value = "前台须知")
    private String qtInfo;

    @ApiModelProperty(value = "${column.comment}")
    private String picture;

    @ApiModelProperty(value = "${column.comment}")
    private String hospitalSubId;

    @ApiModelProperty(value = "${column.comment}")
    private String pyjm;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isShow;

    @ApiModelProperty(value = "${column.comment}")
    private String startTime2;

    @ApiModelProperty(value = "${column.comment}")
    private String endTime2;

    @ApiModelProperty(value = "${column.comment}")
    private String reservationPeriod;

    @ApiModelProperty(value = "${column.comment}")
    private String centerorgname;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isWechatApp;

    @ApiModelProperty(value = "${column.comment}")
    private String queueUrl;

    @ApiModelProperty(value = "${column.comment}")
    private String latGcj;

    @ApiModelProperty(value = "${column.comment}")
    private String lngGcj;

    @ApiModelProperty(value = "${column.comment}")
    private String branchSort;
}

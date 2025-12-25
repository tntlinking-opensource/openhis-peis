package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-01 9:23
 * @description: 分中心信息
 */
@Data
public class SysBranchDto implements Serializable {
    private static final long serialVersionUID = 259306718056449710L;

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

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "纬度")
    private String lat;

    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ApiModelProperty(value = "地址")
    private String address;

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

    @ApiModelProperty(value = "缩略图")
    private String picture;

    @ApiModelProperty(value = "排序")
    private Integer branchSort;


    @ApiModelProperty(value = "距离(km)")
    private Double distance;
}

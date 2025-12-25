package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-31 10:33
 * @description: 待预约团体订单信息
 */
@Data
public class GroupOrderDto implements Serializable {
    private static final long serialVersionUID = 8992724214904324095L;

    //订单ID、订单名称、团体名称、体检号、体检套餐、体检项目信息、体检者姓名、体检者性别
    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "分中心ID集合")
    private String fzxid;

    @ApiModelProperty(value = "预约类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "可预约的分中心列表")
    private List<SysBranchDto> branchList;

    @ApiModelProperty(value = "套餐检查项目列表")
    private List<ItemDto> itemList;


    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "身份证号")
    private Integer idSex;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;


    @ApiModelProperty(value = "分中心名称")
    private String fzx;
}

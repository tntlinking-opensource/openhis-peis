package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 获取预约体检者数据
 */
@Data
public class GetNewReDataDto implements Serializable {
    private static final long serialVersionUID = 3456483770347732210L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检人")
    private String patientname;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "性别 0男1女")
    private String idSex;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "分中心地址")
    private String address;

    @ApiModelProperty(value = "分中心联系电话")
    private String tel;

    @ApiModelProperty(value = "套餐ID")
    private String examsuiteId;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "项目条数")
    private Integer itemsSize;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检套餐ID")
    private String idTjtc;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "原价合计")
    private Double priceTotal;

    @ApiModelProperty(value = "优惠价合计")
    private Double factpriceTotal;

    @ApiModelProperty(value = "状态 1待预约,2已预约,3体检中,4体检结束")
    private String status;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "预约时间段ID")
    private String timeId;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "健康报告地址")
    private String urlPdf;

    @ApiModelProperty(value = "职业报告地址")
    private String urlPdf2;

    @ApiModelProperty(value = "临时报告地址")
    private String urlPdf3;

    @ApiModelProperty(value = "健康报告内容id")
    private String reportContentId;

    @ApiModelProperty(value = "职业报告内容id")
    private String reportContentId2;

    @ApiModelProperty(value = "临时报告内容id")
    private String reportContentId3;

    @ApiModelProperty(value = "项目数据")
    private List<NewReItemsDto> items;

    @ApiModelProperty(value = "订单分中心数据")
    private List<OrderFzxDto> orderFzxDtoList;


}

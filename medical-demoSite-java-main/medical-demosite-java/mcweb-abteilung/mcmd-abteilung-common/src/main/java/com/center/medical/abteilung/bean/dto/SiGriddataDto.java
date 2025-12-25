package com.center.medical.abteilung.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约、登记、保存右侧列表-保存表格数据
 */
@Data
public class SiGriddataDto implements Serializable {
    private static final long serialVersionUID = -2275916348019567625L;

    @ApiModelProperty(value = "状态，removed删除，modified修改，added添加")
    private String state;

    @JsonProperty("changeItem")
    @ApiModelProperty(value = "是否退项退费：0.否 1.是")
    private Integer changeItem;

    @JsonProperty("fMarkFeereturn")
    @ApiModelProperty(value = "退费是否已退：0.否 1.是")
    private Integer fMarkFeereturn;

    @JsonProperty("fGiveup")
    @ApiModelProperty(value = "是否弃检：0.否 1.是")
    private Integer fGiveup;

    @JsonProperty("idExamfeeitem")
    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @JsonProperty("fFeechargedinttrans")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer fFeechargedinttrans;

    @JsonProperty("fDelayed")
    @ApiModelProperty(value = "是否迟检：0.否 1.是")
    private Integer fDelayed;

    @ApiModelProperty(value = "id")
    private String id;

    @JsonProperty("examfeeitemName")
    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @JsonProperty("tempId")
    @ApiModelProperty(value = "tempId")
    private String tempId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @JsonProperty("fRegistered")
    @ApiModelProperty(value = "是否登记：0.否 1.已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "退费价格")
    private Double endtuiprice;

    @JsonProperty("fFeecharged")
    @ApiModelProperty(value = "已收：0.否 1.是")
    private Integer fFeecharged;

    @JsonProperty("idKs")
    @ApiModelProperty(value = "id科室")
    private String idKs;

    @ApiModelProperty(value = "是否加项：0.否 1.是")
    private Integer sfjx;

    @ApiModelProperty(value = "原始价格")
    private Double price;

    @JsonProperty("fExaminated")
    @ApiModelProperty(value = "是否已检：0.否 1.是")
    private Integer fExaminated;

    @ApiModelProperty(value = "实际金额")
    private Double actualprice;

    @ApiModelProperty(value = "收费时间")
    private Date feechargetime;

    @JsonProperty("idPayway")
    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @JsonProperty("idDoctorreg")
    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;


    @ApiModelProperty(value = "个数")
    private Integer count;

    @ApiModelProperty(value = "实付价格")
    private Double factprice;

    @JsonProperty("isMintc")
    @ApiModelProperty(value = "是否是最小套餐：0不是 1是")
    private Integer isMintc;

    @JsonProperty("doctorregR")
    @ApiModelProperty(value = "登记员(冗余)")
    private String doctorregR;

    @ApiModelProperty(value = "备选数量")
    private Integer bxcount;

    @ApiModelProperty(value = "序号")
    private Integer qty;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "是否职业必查：0.否 1.是")
    private Integer zybj;

    @JsonProperty("fLabsendtolis")
    @ApiModelProperty(value = "已发：0.否 1.是")
    private Integer fLabsendtolis;

    @ApiModelProperty(value = "uid")
    private Integer uid;

    @ApiModelProperty(value = "_id")
    private String _id;

}

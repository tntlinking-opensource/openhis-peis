package com.center.medical.sellcrm.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检团体分组保存左下表格数据
 */
@Data
public class GpGriddataDto implements Serializable {
    private static final long serialVersionUID = 3435494208386563557L;

    @JsonProperty("dispOrder")
    @ApiModelProperty(value = "行序")
    private Integer dispOrder;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @JsonProperty("fMale")
    @ApiModelProperty(value = "性别：0男性 1女性 2通用")
    private Integer fMale;

    @JsonProperty("fFemale")
    @ApiModelProperty(value = "女")
    private Integer fFemale;

    @JsonProperty("fHasmarried")
    @ApiModelProperty(value = "已婚")
    private Integer fHasmarried;

    @JsonProperty("fNevermarried")
    @ApiModelProperty(value = "未婚")
    private Integer fNevermarried;

    @JsonProperty("grouptype")
    @ApiModelProperty(value = "分组类型")
    private String grouptype;

    @ApiModelProperty(value = "年龄下限")
    private Integer agemin;

    @ApiModelProperty(value = "年龄上限")
    private Integer agemax;

    @JsonProperty("idPayway")
    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @JsonProperty("harmName")
    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @JsonProperty("idExamtype")
    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "是否自选套餐：0.固定套餐 1.完全自选 2.部分自选")
    private Integer sfzx;

    @ApiModelProperty(value = "分中心名称")
    private String fzx;

    @JsonProperty("fzxId")
    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer forbidden;

    @JsonProperty("idExamsuite")
    @ApiModelProperty(value = "体检套餐ID")
    private String idExamsuite;

    @JsonProperty("tcName")
    @ApiModelProperty(value = "套餐名称")
    private String tcName;

    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @JsonProperty("dateexamplanned")
    @ApiModelProperty(value = "计划体检日期")
    private Date dateexamplanned;

    @JsonProperty("isShowMoney")
    @ApiModelProperty(value = "微信小程序是否展示金额等内容：1隐藏 其他展示")
    private Integer isShowMoney;

    @JsonProperty("idPatientclass2")
    @ApiModelProperty(value = "可重复(线下)")
    private Integer idPatientclass2;


    @JsonProperty("idPatientclass3")
    @ApiModelProperty(value = "可重复(线上)")
    private Integer idPatientclass3;




}

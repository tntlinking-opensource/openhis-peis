package com.center.medical.reception.bean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-03 10:27
 * @description: 登记收费项目信息
 */
@Data
public class ItemsDto implements Serializable {
    private static final long serialVersionUID = 2560108014928675242L;

    @ApiModelProperty(value = "序号")
    private Integer qty;

    @ApiModelProperty(value = "检查收费项目记录ID")
    private String id;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "收费项目名称:必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”")
    private String examfeeitemName;

    @ApiModelProperty(value = "原始价格")
    private Double price;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "是否加项：0.否 1.是")
    private Integer sfjx;

    @ApiModelProperty(value = "退费价格")
    private Double endtuiprice;

    @ApiModelProperty(value = "加项医师ID")
    private String jxys;

    @JsonProperty("FRegistered")
    @ApiModelProperty(value = "是否登记：0.否 1.已登记")
    private Integer fRegistered;

    @ApiModelProperty(value = "是否退费：0.否 1.是")
    private Integer changeItem;

    @JsonProperty("FMarkFeereturn")
    @ApiModelProperty(value = "已退：0.否 1.是")
    private Integer fMarkFeereturn;

    @JsonProperty("FFeecharged")
    @ApiModelProperty(value = "已收：0.否 1.是")
    private Integer fFeecharged;

    @JsonProperty("FLabsendtolis")
    @ApiModelProperty(value = "已发：0.否 1.是")
    private Integer fLabsendtolis;

    @JsonProperty("FExaminated")
    @ApiModelProperty(value = "是否已检：0.否 1.是")
    private Integer fExaminated;

    @JsonProperty("FGiveup")
    @ApiModelProperty(value = "是否弃检：0.否 1.是")
    private Integer fGiveup;

    @JsonProperty("FDelayed")
    @ApiModelProperty(value = "是否迟检：0.否 1.是")
    private Integer fDelayed;

    @ApiModelProperty(value = "是否拒检：0.否 1.是")
    private Integer sfjj;

    @ApiModelProperty(value = "备注")
    private String feeitemdesc;

    @ApiModelProperty(value = "是否是最小套餐：0不是 1是")
    private Integer isMintc;

    @JsonProperty("FFeechargedinttrans")
    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer fFeechargedinttrans;

    @ApiModelProperty(value = "是否备选：0.否 1.是")
    private Integer isbx;

    @ApiModelProperty(value = "科室ID")
    private String idKs;

    @ApiModelProperty(value = "科室临时加项ID")
    private String tempId;

    @ApiModelProperty(value = "登记人：userName")
    private String doctorregR;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "_id")
    private Integer _id;

    @ApiModelProperty(value = "_uid")
    private Integer _uid;

    @ApiModelProperty(value = "收费项目类型：removed：删除、modified：变更（弃检、退项、迟检、补检、拒检）、added：加项")
    private String _state;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

}

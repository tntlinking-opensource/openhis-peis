package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检卡管理-卡消费明细分页返回数据
 */
@Data
public class SDPageVo implements Serializable {
    private static final long serialVersionUID = -5755914447078730112L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "卡号")
    @ApiModelProperty(value = "会员卡号")
    private String cardId;

    @Excel(name = "卡类型")
    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String chargeId;

    @Excel(name = "操作人")
    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @Excel(name = "操作日期", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作日期")
    private Date operationTime;

    @Excel(name = "金额类型", readConverterExp = "0=充值,1=消费")
    @ApiModelProperty(value = "是否为增加：0.为充值 1.为消费")
    private Integer isAdd;

    @Excel(name = "消费类型", readConverterExp = "null=体检,0=体检,1=药房,2=口腔科,3=眼镜店,4=合作,5=保健品")
    @ApiModelProperty(value = "消费类型，消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品")
    private Integer consumetype;

    @Excel(name = "金额")
    @ApiModelProperty(value = "记录增加或减少的金额或者积分")
    private Double limit;

    @Excel(name = "剩余金额")
    @ApiModelProperty(value = "操作后金额")
    private Double handleMoney;


    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "卡备注")
    private String kbz;

    @ApiModelProperty(value = "有效期")
    private Date yxq;

    @ApiModelProperty(value = "卡标识")
    private String kbs;

    @ApiModelProperty(value = "卡说明")
    private String ksm;

    @ApiModelProperty(value = "体检者姓名")
    private String tjzxm;

    @ApiModelProperty(value = "金额实付")
    private Double jsf;


}

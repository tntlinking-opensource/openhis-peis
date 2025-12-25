package com.center.medical.finance.bean.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检卡管理-卡充值分页返回数据
 */
@Data
public class RechargeCardVo implements Serializable {

    private static final long serialVersionUID = 4236152910354537517L;

    @TableField(exist = false)
    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检卡号")
    @ApiModelProperty(value = "体检卡ID")
    private String cardId;

    @Excel(name = "卡类型")
    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @Excel(name = "金额类型" ,readConverterExp = "0=充值,1=消费")
    @ApiModelProperty(value = "是否是加项收费   1 是  null不是")
    private Integer isAdd;

    @Excel(name = "消费类型" ,readConverterExp = "0=体检,1=药房,2=口腔科,3=眼镜店,4=合作,5=保健品,null=体检")
    @ApiModelProperty(value = "消费类型，消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品")
    private Integer consumetype;

    @Excel(name = "金额")
    @ApiModelProperty(value = "记录增加或减少的金额或者积分")
    private Double limit;

    @Excel(name = "操作后金额")
    @ApiModelProperty(value = "操作后金额")
    private Double handleMoney;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "收费项目ID(多个 逗号分隔)")
    private String chargeId;

    @Excel(name = "体检者姓名")
    @ApiModelProperty(value = "名称")
    private String tjzxm;

    @Excel(name = "结算费")
    @ApiModelProperty(value = "金额实付")
    private Double jsf;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String memotext;

    @Excel(name = "操作人")
    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @Excel(name = "卡备注")
    @ApiModelProperty(value = "卡备注")
    private String kbz;

    @Excel(name = "有效期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "有效期")
    private Date yxq;

    @Excel(name = "操作日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作日期")
    private Date operationTime;

    @Excel(name = "卡标识")
    @ApiModelProperty(value = "卡标识")
    private String kbs;

    @Excel(name = "卡说明")
    @ApiModelProperty(value = "卡说明")
    private String ksm;


}

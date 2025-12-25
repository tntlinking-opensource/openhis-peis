package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CW卡(Card)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:29
 */
@Data
@TableName("CARD")
@ApiModel(value = "Card", description = "CW卡实体类")
public class Card extends Model<Card> implements Serializable {
    private static final long serialVersionUID = -98712948941626691L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "卡内剩余的金额或者积分")
    private Double balanceLimit;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "创建人ID")
    private String createId;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "更新人ID")
    private String modifyId;

    @ApiModelProperty(value = "卡类型ID")
    private String typeId;

    @ApiModelProperty(value = "售卡人ID")
    private String sellId;

    @ApiModelProperty(value = "出售时间")
    private Date sellTime;

    @ApiModelProperty(value = "有效期")
    private Date validity;

    @ApiModelProperty(value = "卡说明")
    private String cardState;

    @ApiModelProperty(value = "卡备注")
    private String memo;

    @ApiModelProperty(value = "领取人ID")
    private String getterId;

    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    @ApiModelProperty(value = "发卡人ID")
    private String grantId;

    @ApiModelProperty(value = "假删")
    private Double isDelete;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "会员卡剩余积分（仅限会员卡使用）")
    private Integer balanceJf;

    @ApiModelProperty(value = "${column.comment}")
    private String payId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer skMoney;

    @ApiModelProperty(value = "${column.comment}")
    private Double isDeletea;

    @ApiModelProperty(value = "${column.comment}")
    private String password;

    @ApiModelProperty(value = "${column.comment}")
    private String tel;

    @ApiModelProperty(value = "${column.comment}")
    private String orderId;

    @ApiModelProperty(value = "${column.comment}")
    private String tcId;

    @ApiModelProperty(value = "${column.comment}")
    private Double balanceMoney;

    @ApiModelProperty(value = "${column.comment}")
    private Date tcDateregister;

    @ApiModelProperty(value = "${column.comment}")
    private String tcPatientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tcChecked;

    @ApiModelProperty(value = "${column.comment}")
    private Double recheckMoney;

    @ApiModelProperty(value = "${column.comment}")
    private Integer sellStatus;

    @ApiModelProperty(value = "${column.comment}")
    private Double tcPrice;

    @ApiModelProperty(value = "${column.comment}")
    private Double sellPrice;

    @ApiModelProperty(value = "${column.comment}")
    private String purchaser;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private Double zk;

    @ApiModelProperty(value = "${column.comment}")
    private String processId;
}

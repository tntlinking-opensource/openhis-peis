package com.center.medical.finance.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检卡(Card)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_card")
@ApiModel(value = "Card", description = "体检卡实体类")
public class Card extends Model<Card> implements Serializable {
    private static final long serialVersionUID = 357387831398069194L;

    public static final String JTK = "60";//家庭卡卡类型id
    public static final String TEN = "23";//十周年卡类型id
    public static final String JF = "18";//积分收费方式id
    public static final String YE = "19";//余额收费方式id
    public static final String GROUP = "6";//团检专属卡类型id
    public static final String ACT = "5";//活动专属卡类型id


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "卡内剩余的金额(会员卡、体检卡)")
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

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "卡前缀")
    private String cardPrefix;

    @ApiModelProperty(value = "卡标识")
    private String cardLogo;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "支付方式ID")
    private String payId;

    @ApiModelProperty(value = "收款金额")
    private Double skMoney;

    @ApiModelProperty(value = "体检卡管理假删标识")
    private Double isDeletea;

    @ApiModelProperty(value = "体检卡密码")
    private String password;

    @ApiModelProperty(value = "体检卡绑定的app userid")
    private String tel;

    @ApiModelProperty(value = "订单ID（团检专属卡）")
    private String orderId;

    @ApiModelProperty(value = "套餐id（团检专属卡)||十周年  最小套餐id")
    private String tcId;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;

    @ApiModelProperty(value = "使用套餐完成登记的时间")
    private Date tcDateregister;

    @ApiModelProperty(value = "使用赠送套餐完成登记的体检号")
    private String tcPatientcode;

    @ApiModelProperty(value = "赠送套餐是否已用：0或null.否 1.是")
    private Integer tcChecked;

    @ApiModelProperty(value = "复查额度（体检时间一年后到期（判断套餐登记日期，必须要有赠送套餐）  按钮手动扣除）")
    private Double recheckMoney;

    @ApiModelProperty(value = "销售状态：0或null未售 1.已售")
    private Integer sellStatus;

    @ApiModelProperty(value = "面值（活动专属卡和团检专属卡即套餐优惠价，其他卡就是初始金额）")
    private Double tcPrice;

    @ApiModelProperty(value = "售价")
    private Double sellPrice;

    @ApiModelProperty(value = "购卡人名字（不一定在系统中）")
    private String purchaser;

    @ApiModelProperty(value = "购卡人手机号")
    private String phone;

    @ApiModelProperty(value = "折扣")
    private Double zk;

    @ApiModelProperty(value = "卡办理id 对应card_payway processId")
    private String processId;

    @TableField(exist = false)
    @ApiModelProperty(value = "卡类型名称")
    private String typeName;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @TableField(exist = false)
    @ApiModelProperty(value = "售卡人姓名")
    private String sellName;
}

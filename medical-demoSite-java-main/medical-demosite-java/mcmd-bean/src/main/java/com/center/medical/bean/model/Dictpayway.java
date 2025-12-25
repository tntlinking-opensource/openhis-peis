package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC支付方式表(Dictpayway)表实体类
 *
 * @author 路飞船长
 * @since 2023-03-04 14:49:32
 */
@Data
@TableName("md_dictpayway")
@ApiModel(value = "Dictpayway", description = "JC支付方式表实体类")
public class Dictpayway extends Model<Dictpayway> implements Serializable {

    public static String FREE = "12";//免费id
    public static String XJ = "1";//现金
    public static String TS = "5";//统收
    public static String HDZS = "20";//活动赠送
    public static String KT = "ff80808172a5f6210172a809a9cb2222";//康淘
    public static String TONGLIAN = "22";//通联


    private static final long serialVersionUID = 820258074839957549L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "支付方式代码: HIS接口标识")
    private String paywayCode;

    @ApiModelProperty(value = "是否可退现金：0.否 1.是")
    private Integer fReturntocash;

    @ApiModelProperty(value = "团体发票ID")
    private String idReceipttypeOrg;

    @ApiModelProperty(value = "个人发票ID")
    private String idReceipttypePerson;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否记录提成：0.否 1.是 (客服个检统计中只记录提成的)")
    private Integer fHideondailyreport;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否可以编辑卡号：0.可以 1.不可以")
    private Integer isChange;

    @ApiModelProperty(value = "个检金蝶支付名")
    private String thingKingdeePaywayname;

    @ApiModelProperty(value = "个检金蝶状态")
    private String thingKingdeeUseStatus;

    @ApiModelProperty(value = "团检金蝶编号")
    private String groupKingdeeNumber;

    @ApiModelProperty(value = "团检金蝶支付名")
    private String groupKingdeePaywayname;

    @ApiModelProperty(value = "团检金蝶状态")
    private String groupKingdeeUseStatus;

    @ApiModelProperty(value = "记账结算金蝶编码")
    private String posKingdeeNumber;

    @ApiModelProperty(value = "记账结算金蝶支付名")
    private String posKingdeePaywayname;

    @ApiModelProperty(value = "记账结算金蝶状态")
    private String posKingdeeUseStatus;

    @ApiModelProperty(value = "手动维护上传时候的客户名")
    private String kingdeeCompany;

    @ApiModelProperty(value = "金蝶业务员")
    private String kingdeeSaleer;

    @ApiModelProperty(value = "个检金蝶编号")
    private String thingKingdeeNumber;

    @ApiModelProperty(value = "是否支持疫苗统计：0.否 1.是")
    private Integer vaccine;

    @ApiModelProperty(value = "支付配置参数")
    private String payConfig;

    @ApiModelProperty(value = "可使用的地方, 如1,2,3,4：空则表示通用，1.财务、2.个检、3.团检、4.住院、5.门诊")
    private String usableIn;

    @ApiModelProperty(value = "排列顺序")
    private Integer seq;

    @ApiModelProperty(value = "状态：0.禁用 1.正常")
    private Integer status;
}

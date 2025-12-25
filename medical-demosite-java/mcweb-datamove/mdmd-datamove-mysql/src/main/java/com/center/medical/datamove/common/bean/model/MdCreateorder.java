package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 订单表(MdCreateorder)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
@Data
@TableName("md_createorder")
@ApiModel(value = "MdCreateorder", description = "订单表实体类")
public class MdCreateorder extends Model<MdCreateorder> implements Serializable {
    private static final long serialVersionUID = 505427873280488275L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "订单代码")
    private String dddm;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "提醒方式：0.首页提醒 1.短信提醒")
    private Integer txfs;

    @ApiModelProperty(value = "发送短信手机号")
    private String fsdxsjh;

    @ApiModelProperty(value = "创建订单日期")
    private Date cjddrq;

    @ApiModelProperty(value = "客户单位电话")
    private String khdwdh;

    @ApiModelProperty(value = "体检中心")
    private String tjzx;

    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "是否已签订合同")
    private String sfyqdht;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "男性体检人数")
    private Integer nxtjrs;

    @ApiModelProperty(value = "女性体检人数")
    private Integer vxtjrs;

    @ApiModelProperty(value = "订单价格")
    private Double ddjg;

    @ApiModelProperty(value = "订单折扣")
    private Double ddzk;

    @ApiModelProperty(value = "订单优惠价")
    private Double ddyhj;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "审批状态，详见：com.world.center.bean.enums.OrderCheckStatus")
    private Integer spzt;

    @ApiModelProperty(value = "审批意见")
    private String spyj;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "文件路径(名单)")
    private String urls;

    @ApiModelProperty(value = "变更状态，详见：com.world.center.bean.enums.OrderChangeStatus")
    private Integer bgzt;

    @ApiModelProperty(value = "文件路径(名单)")
    private String xsdh;

    @ApiModelProperty(value = "体检形式：0.内检 1.外检 2.内检加外检")
    private Integer tjxs;

    @ApiModelProperty(value = "材料文件路径s（|分隔）")
    private String clurls;

    @ApiModelProperty(value = "材料审批状态：null未审核 1.通过 2.驳回")
    private Integer clspzt;

    @ApiModelProperty(value = "材料审批意见")
    private String clspyj;

    @ApiModelProperty(value = "通知方式ID")
    private String idInforway;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "审批人")
    private String spr;

    @ApiModelProperty(value = "材料审批人")
    private String clspr;

    @ApiModelProperty(value = "是否线上备单：null或其他.未线上备单 1.已线上备单")
    private Integer isOnline;

    @ApiModelProperty(value = "提交时间")
    private Date submitTime;

    @ApiModelProperty(value = "是否不可替检：0或null可替检 1.不可替检")
    private Integer cantReplace;

    @ApiModelProperty(value = "柜子号，同步订单时，直接往线下柜子号中同步生成相应的柜子号记录（chest）")
    private String chestNum;

    @ApiModelProperty(value = "变更备注")
    private String bgmemo;

    @ApiModelProperty(value = "复查收费方式")
    private String reviewPayway;

    @ApiModelProperty(value = "复查优惠折扣")
    private Double reviewZk;

    @ApiModelProperty(value = "模板类型，分中心简码")
    private String templateJm;

    @ApiModelProperty(value = "模板类型，分中心名称（用于显示）")
    private String templateText;

    @ApiModelProperty(value = "开单助理用户名")
    private String kdzlName;

    @ApiModelProperty(value = "是否执行市价（如果执行市价，登记时添加的项目，价格都使用收费项目市场价）")
    private Integer isMarket;
}

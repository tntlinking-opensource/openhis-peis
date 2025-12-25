package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.OrderChangeStatus;
import com.center.medical.bean.enums.OrderCheckStatus;
import com.center.medical.common.annotation.Excel;
import com.center.medical.sellcrm.bean.param.TjtcIdParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单表(Createorder)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:57
 */
@Data
@TableName("md_createorder")
@ApiModel(value = "Createorder", description = "订单表实体类")
public class Createorder extends Model<Createorder> implements Serializable {
    private static final long serialVersionUID = -23426796929493163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "订单名称")
    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    /**
     * @see OrderCheckStatus
     */
    @Excel(name = "订单状态",readConverterExp = "0=审核未通过,1=已撤回,2=草稿,3=已提交,4=审核通过")
    @ApiModelProperty(value = "审批状态：0.审核未通过  1.已撤回 2.草稿 3.已提交 4.审核通过")
    private Integer spzt;

    @Excel(name = "变更状态",readConverterExp = "0=审核未通过,2=已变更,3=变更已提交,4=变更审核通过,5=未变更,null=未变更")
    @ApiModelProperty(value = "变更状态，订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更")
    private Integer bgzt;

    @Excel(name = "客户单位名称")
    @TableField(exist = false)
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "分中心")
    @TableField(exist = false)
    @ApiModelProperty(value = "分中心的名字，拼接")
    private String fzx;

    @Excel(name = "备单情况",readConverterExp = "0=未备单,null=未备单")
    @TableField(exist = false)
    @ApiModelProperty(value = "备单情况，1.null表示未备单 2.有值的后面加”已备单“三个字")
    private String bdqk;

    @ApiModelProperty(value = "订单代码")
    private String dddm;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "发送短信手机号")
    private String fsdxsjh;

    @ApiModelProperty(value = "体检中心")
    private String tjzx;

    @Excel(name = "计划检期从",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划检期从")
    private Date jhjqc;

    @Excel(name = "计划检期到",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划检期到")
    private Date jhjqd;

    @ApiModelProperty(value = "是否已签订合同")
    private String sfyqdht;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @Excel(name = "男性体检人数")
    @ApiModelProperty(value = "男性体检人数")
    private Integer nxtjrs;

    @Excel(name = "女性体检人数")
    @ApiModelProperty(value = "女性体检人数")
    private Integer vxtjrs;

    @Excel(name = "订单价格")
    @ApiModelProperty(value = "订单价格")
    private Double ddjg;

    @Excel(name = "订单折扣")
    @ApiModelProperty(value = "订单折扣")
    private Double ddzk;

    @Excel(name = "套餐平均价")
    @ApiModelProperty(value = "订单优惠价")
    private Double ddyhj;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "客户单位电话")
    @ApiModelProperty(value = "客户单位电话")
    private String khdwdh;

    @Excel(name = "创建订单日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建订单日期")
    private Date cjddrq;

    @Excel(name = "审批意见")
    @ApiModelProperty(value = "审批意见")
    private String spyj;

    @Excel(name = "提醒方式",readConverterExp = "0=首页提醒,1=短信提醒")
    @ApiModelProperty(value = "提醒方式：0.首页提醒 1.短信提醒")
    private Integer txfs;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "文件路径(名单)")
    private String urls;

    /**
     * @see OrderChangeStatus
     */


    @ApiModelProperty(value = "文件路径(名单)")
    private String xsdh;

    @ApiModelProperty(value = "体检形式：0.内检 1.外检 2.内检加外检")
    private Integer tjxs;

    @ApiModelProperty(value = "材料文件路径s（|分隔）")
    private String clurls;

    @ApiModelProperty(value = "材料审批状态：null未审核 1.通过 2.驳回 就是分页里面的fclspzt")
    private Integer clspzt;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
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

    @TableField(exist = false)
    @ApiModelProperty(value = "变更标志,1是变更，其他编辑")
    private String fchange;

    @TableField(exist = false)
    @ApiModelProperty(value = "拼接的套餐id(与订单关联的套餐id)")
    private List<TjtcIdParam> tjtcId;

    @TableField(exist = false)
    @ApiModelProperty(value = "套餐状态,存入中间表,用于区分从那张表中取数据(0:普通套餐、1/2：最小套餐)")
    private String comboState;



    @TableField(exist = false)
    @ApiModelProperty(value = "xzqk")
    private String xzqk;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检码")
    private String tjm;



    @TableField(exist = false)
    @ApiModelProperty(value = "名单状态 1已上传 0未上传")
    private String mdzt;


    @TableField(exist = false)
    @ApiModelProperty(value = "开单助理")
    private String kdzl;


    @TableField(exist = false)
    @ApiModelProperty(value = "平均变动成本率以及历史变动成本率")
    private String orderHistoryRates;


    @TableField(exist = false)
    @ApiModelProperty(value = "变动成本率")
    private Double varCostRate;

}

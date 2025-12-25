package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.Fkfs;
import com.center.medical.bean.enums.ZYTJLB;
import com.center.medical.data.bean.model.Harm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 普通套餐表(Createmeal)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-15 09:31:06
 */
@Data
@TableName("md_createmeal")
@ApiModel(value = "Createmeal", description = "普通套餐表实体类")
public class Createmeal extends Model<Createmeal> implements Serializable {
    private static final long serialVersionUID = 265786877031341642L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检类型[0.健康体检 1.职业体检 2.综合 3.复查]")
    private Integer tjlx;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "适用性别[0.男 1.女 其他.通用]")
    private Integer syxb;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "是否已备单")
    private String sfybd;

    @ApiModelProperty(value = "是否已婚套餐[0.是 1.否 其他.通用]")
    private String sfyhtc;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    /**
     * @see Fkfs
     */
    @ApiModelProperty(value = "付款方式：0.现金 1.统收")
    private String fkfs;

    /**
     * @see ZYTJLB
     */
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private Integer zytjlb;

    @ApiModelProperty(value = "普通套餐标示：0.普通套餐")
    private String combostate;

    @ApiModelProperty(value = "可选数量")
    private Integer kxsl;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String fzx;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "编辑状态：0.可编辑 1.不可编辑")
    private Integer bjzt;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "团检加项/弃项用：0.未修改 1.已用于加项/弃项")
    private Integer isOrgPlus;

    @ApiModelProperty(value = "折扣状态位：0.可用折扣打折 1.可用领导折扣打折")
    private Integer zkztw;

    @ApiModelProperty(value = "体检码")
    private Integer tjm;

    @ApiModelProperty(value = "是否自选套餐：0.固定套餐 1.完全自选 2.部分自选")
    private Integer sfzx;

    @ApiModelProperty(value = "是否外出：0 .内检 1.外检")
    private Integer sfwc;

    @ApiModelProperty(value = "自选折扣（用于APP计算自选项目价格）")
    private Double zxzk;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer forbidden;

    @ApiModelProperty(value = "平安套餐ID")
    private String pinganId;

    @ApiModelProperty(value = "是否大套餐：0或null.否 1.是")
    private Integer isBig;

    @ApiModelProperty(value = "统收限额")
    private Double tongLimit;

    @ApiModelProperty(value = "团惠价，优惠价不包括备选项目（app使用）")
    private Double zhjgapp;

    @ApiModelProperty(value = "分组金额")
    private Double groupPrice;

    @ApiModelProperty(value = "项目成本价合计")
    private Double totalCostprice;

    @ApiModelProperty(value = "备注")
    private String bz;

    @TableField(exist = false)
    @ApiModelProperty(value = "关联的接害因素列表")
    private List<Harm> harmList;

    @TableField(exist = false)
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称，多个以英文逗号（,）隔开")
    private String fzxName;

    @TableField(exist = false)
    @ApiModelProperty(value = "拼接接害因素")
    private String jhysName;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心ID--存在多个分中心")
    private String fzxids;

    @TableField(exist = false)
    @ApiModelProperty(value = "电话")
    private String phone;

    @TableField(exist = false)
    @ApiModelProperty(value = "成本价")
    private Double costprice;


    @TableField(exist = false)
    @ApiModelProperty(value = "变动成本率")
    private String varCostRate;


    @TableField(exist = false)
    @ApiModelProperty(value = "app套餐id，这个不为空即是app套餐")
    private String appId;
}

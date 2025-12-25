package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单与套餐关联表(Orderandcombo)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:07
 */
@Data
@TableName("md_orderandcombo")
@ApiModel(value = "Orderandcombo", description = "订单与套餐关联表实体类")
public class Orderandcombo extends Model<Orderandcombo> implements Serializable {
    private static final long serialVersionUID = 828904912819814074L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddid;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "套餐状态--用于区分从哪张表中取套餐(0:普通套餐、1/2:最小套餐)")
    private String combostate;

    @ApiModelProperty(value = "0.否 1.是--前台需要")
    private Integer isjj;

    @ApiModelProperty(value = "是否已备单 0.否 1.是")
    private String sfybd;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "审批状态：0.未审批 1.已审批")
    private Integer spzt;

    @ApiModelProperty(value = "是否变更：1是最近一次变更")
    private Integer isbg;

    @TableField(value = "`show`")
    @ApiModelProperty(value = "是否显示：0或者Null展示 1.隐藏")
    private Integer show;

    @ApiModelProperty(value = "检查类型，0.健康类 1.职业类 2.综合类 5.入职类 6.疫苗类 7.其他类")
    private Integer idExamclass;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心id")
    private String fzxid;


    @TableField(exist = false)
    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;


    @TableField(exist = false)
    @ApiModelProperty(value = "体检类型[0.健康体检 1.职业体检 2.综合 3.复查]")
    private Integer tjlx;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检类型[0.健康体检 1.职业体检 2.综合 3.复查]")
    private Integer ftjlx;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @TableField(exist = false)
    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @TableField(exist = false)
    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer forbid;

    @TableField(exist = false)
    @ApiModelProperty(value = "拼接接害因素名称")
    private String jhysName;

    @TableField(exist = false)
    @ApiModelProperty(value = "0男、1女、其他通用")
    private Integer syxb;

    @TableField(exist = false)
    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @TableField(exist = false)
    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @TableField(exist = false)
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否已婚套餐[0.是 1.否 其他.通用]")
    private String sfyhtc;

    @TableField(exist = false)
    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @TableField(exist = false)
    @ApiModelProperty(value = "年龄至")
    private String nld;

    @TableField(exist = false)
    @ApiModelProperty(value = "付款方式：0.统收 1.自费")
    private String fkfs;

    @TableField(exist = false)
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String zytjlb;

    @TableField(exist = false)
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String jhysV;


    @TableField(exist = false)
    @ApiModelProperty(value = "分中心名称")
    private String fzxName;

}

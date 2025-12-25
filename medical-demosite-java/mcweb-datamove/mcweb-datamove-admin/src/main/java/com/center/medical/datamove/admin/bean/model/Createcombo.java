package com.center.medical.datamove.admin.bean.model;


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
 * 套餐表(Createcombo)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:56:41
 */
@Data
@TableName("CREATECOMBO")
@ApiModel(value = "Createcombo", description = "套餐表实体类")
public class Createcombo extends Model<Createcombo> implements Serializable {
    private static final long serialVersionUID = -94366443333517490L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "{'id':'0','text':'上岗前'},{'id':'1','text':'在岗期间'},{'id':'2','text':'离岗时'},{'id':'3','text':'离岗后'},{'id':'4','text':'应急'}")
    private String zytjlb;

    @ApiModelProperty(value = "适用性别")
    private Integer syxb;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;

    @ApiModelProperty(value = "套餐折扣")
    private Double tczk;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位名称ID")
    private String khdwmcid;

    @ApiModelProperty(value = "是否已备单")
    private String sfybd;

    @ApiModelProperty(value = "是否已婚套餐")
    private String sfyhtc;

    @ApiModelProperty(value = "年龄自")
    private String nlz;

    @ApiModelProperty(value = "年龄至")
    private String nld;

    @ApiModelProperty(value = "付款方式")
    private String fkfs;

    @ApiModelProperty(value = "数量")
    private Integer sl;

    @ApiModelProperty(value = "几选几")
    private String jxj;

    @ApiModelProperty(value = "1：健康体检最小套餐 2：职业体检最小套餐")
    private String combostate;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "假删状态")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "编辑状态（0：可编辑、1：不可编辑）")
    private Integer bjzt;

    @ApiModelProperty(value = "团检加项、弃项用 0：不是加项、弃项 1：是加项、弃项")
    private Integer isPlus;

    @ApiModelProperty(value = "${column.comment}")
    private Integer tbzt;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isActive;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isRecommended;

    @ApiModelProperty(value = "${column.comment}")
    private Integer comboSort;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isBan;

    @ApiModelProperty(value = "${column.comment}")
    private String pinganId;

    @ApiModelProperty(value = "${column.comment}")
    private String appTypeId;

    @ApiModelProperty(value = "${column.comment}")
    private String modifier;
}

package com.center.medical.reception.bean.model;


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
 * 最小套餐(MdCreatecombo)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:58:11
 */
@Data
@TableName("md_createcombo")
@ApiModel(value = "MdCreatecombo", description = "最小套餐实体类")
public class MdCreatecombo extends Model<MdCreatecombo> implements Serializable {
    private static final long serialVersionUID = 482648933498593816L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "职业体检类别，详见：com.world.center.bean.enums.MedicalType")
    private Integer zytjlb;

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

    @ApiModelProperty(value = "最小套餐标识：1.健康体检最小套餐 2.职业体检最小套餐")
    private String combostate;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID：创建所在的分中心")
    private String fzxid;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "编辑状态：0.可编辑 1.不可编辑")
    private Integer bjzt;

    @ApiModelProperty(value = "团检加项/弃项用：0.不是加项/弃项 1.是加项/弃项")
    private Integer isPlus;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步")
    private Integer tbzt;

    @ApiModelProperty(value = "是否活动：0或null.否 1.是")
    private Integer isActive;

    @ApiModelProperty(value = "是否推荐：0或null.否 1.是")
    private Integer isRecommended;

    @ApiModelProperty(value = "app排序")
    private Integer comboSort;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer isBan;

    @ApiModelProperty(value = "平安套餐ID")
    private String pinganId;

    @ApiModelProperty(value = "小程序分类id")
    private String appTypeId;

    @ApiModelProperty(value = "修改人用户名")
    private String modifier;
}

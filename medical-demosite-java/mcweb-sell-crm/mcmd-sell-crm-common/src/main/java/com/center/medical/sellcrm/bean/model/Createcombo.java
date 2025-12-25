package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.ZYTJLB;
import com.center.medical.sellcrm.bean.vo.ComboAndItemVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 最小套餐(Createcombo)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-15 09:31:06
 */
@Data
@TableName("md_createcombo")
@ApiModel(value = "Createcombo", description = "最小套餐实体类")
public class Createcombo extends Model<Createcombo> implements Serializable {
    private static final long serialVersionUID = 653202594124648867L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "套餐ID")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlx;

    @ApiModelProperty(value = "体检套餐简称")
    private String tjtcjc;

    @ApiModelProperty(value = "体检套餐输入码")
    private String tjtcsrm;

    @ApiModelProperty(value = "接害因素id")
    private String jhys;

    /**
     * @see ZYTJLB
     */
    @ApiModelProperty(value = "职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String zytjlb;

    /**
     * @see ZYTJLB
     */
    @ApiModelProperty(value = "适用性别：0.男 1.女 2.通用")
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

    @ApiModelProperty(value = "是否已婚套餐[0.是 1.否 其他.通用]")
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

    @ApiModelProperty(value = "项目成本价合计")
    private Double totalCostprice;

    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @TableField(exist = false)
    @ApiModelProperty(value = "已关联检查项目数量")
    private Integer num;

    @TableField(exist = false)
    @ApiModelProperty(value = "接害因素名称")
    private String jhysmc;

    @TableField(exist = false)
    @ApiModelProperty(value = "关联的收费项目")
    private List<ComboAndItemVo> comboAndItemVoList;


    @TableField(exist = false)
    @ApiModelProperty(value = "appId")
    private String appId;
}

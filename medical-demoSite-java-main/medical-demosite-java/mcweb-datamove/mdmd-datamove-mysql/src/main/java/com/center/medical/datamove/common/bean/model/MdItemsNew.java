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
 * JC收费项目表(MdItemsNew)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:24
 */
@Data
@TableName("md_items_new")
@ApiModel(value = "MdItemsNew", description = "JC收费项目表实体类")
public class MdItemsNew extends Model<MdItemsNew> implements Serializable {
    private static final long serialVersionUID = 750465109157259183L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "系统维护标记")
    private String sysmanmark;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目名称缩写")
    private String examfeeitemNameabr;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "收费项目英语名称")
    private String examfeeitemNameeng;

    @ApiModelProperty(value = "收费项目英语名称缩写")
    private String examfeeitemNameengabr;

    @ApiModelProperty(value = "收费项目代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "部位ID")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "是否在APP出现：1.是  0或null.否")
    private String examfeeitemCodex;

    @ApiModelProperty(value = "条码打印分类id")
    private String examfeeitemClass;

    @ApiModelProperty(value = "创建人")
    private String xXxdm;

    @ApiModelProperty(value = "禁止打折字段，1禁止打折")
    private Integer fDiscountdisabled;

    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @ApiModelProperty(value = "套餐价格")
    private Double suiteprice;

    @ApiModelProperty(value = "耗材价格")
    private Double materialprice;

    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "外部价")
    private Double coopprice;

    @ApiModelProperty(value = "外送机构ID")
    private String idCooporg;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @ApiModelProperty(value = "X_YBDM")
    private String xYbdm;

    @ApiModelProperty(value = "标本类型ID")
    private String idLabtype;

    @ApiModelProperty(value = "标本类型名称")
    private String labtypeR;

    @ApiModelProperty(value = "app分类ID")
    private String labtypeSub;

    @ApiModelProperty(value = "图片路径")
    private String inputcodee;

    @ApiModelProperty(value = "必选项目ids")
    private String guidesheetcode;

    @ApiModelProperty(value = "上次统计检查次数时间（体检者收费项目创建时间）")
    private Date dtLastautoinsert;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "导引单分组")
    private String dydfz;

    @ApiModelProperty(value = "序号")
    private String xh;

    @ApiModelProperty(value = "导引单打印标示")
    private String dyddybs;

    @ApiModelProperty(value = "折扣")
    private Double zk;

    @ApiModelProperty(value = "外宾价格")
    private Double wbjg;

    @ApiModelProperty(value = "优待价格")
    private Double ydjg;

    @ApiModelProperty(value = "内部价")
    private Double nbj;

    @ApiModelProperty(value = "样本类型")
    private String yblx;

    @ApiModelProperty(value = "独立标示")
    private String dlbs;

    @ApiModelProperty(value = "样本类型名称")
    private String yblxmc;

    @ApiModelProperty(value = "费用类型")
    private String fylx;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "餐序")
    private String cx;

    @ApiModelProperty(value = "样本类型ID")
    private String yblxid;

    @ApiModelProperty(value = "体检类型")
    private String tjlx;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "标示")
    private String bs;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "隐私项目 1是0不是 是则不出现在团检报告、个检报告、总检中,只出现在隐私报告(乙肝五项等项目为个人隐私，依照法律未经个人统一，不可出现在团检报告中)")
    private String bgdybs;

    @ApiModelProperty(value = "销售打印分类")
    private String xsdyfl;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "复查注意事项")
    private String reviewMatters;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "检查次数")
    private Integer jccs;

    @ApiModelProperty(value = "收费项目intID")
    private Integer examfeeitemid;

    @ApiModelProperty(value = "是否是公共的")
    private Integer isPublic;

    @ApiModelProperty(value = "分中心IDs，逗号拼接")
    private String fzxIds;

    @ApiModelProperty(value = "排检人数上限(废弃)")
    private Integer preUpperLimit;

    @ApiModelProperty(value = "是否禁用：1.是  0或null.否")
    private String isBan;

    @ApiModelProperty(value = "条码打印个数")
    private Integer barcodeCount;

    @ApiModelProperty(value = "条码打印名称")
    private String barcodeName;

    @ApiModelProperty(value = "序号，职业团检报告 五、检查项目 排序")
    private Integer groupOrder;
}

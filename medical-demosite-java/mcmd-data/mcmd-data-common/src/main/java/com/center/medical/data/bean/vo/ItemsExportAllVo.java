package com.center.medical.data.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费项目设置 导出数据
 */
@Data
public class ItemsExportAllVo implements Serializable {
    private static final long serialVersionUID = -2684925923387986538L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "输入码")
    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @Excel(name = "接口代码")
    @ApiModelProperty(value = "收费项目代码")
    private String examfeeitemCode;

    @Excel(name = "导引单分组")
    @ApiModelProperty(value = "导引单分组")
    private String dydfz;

    @Excel(name = "体检类型", readConverterExp = "0=健康,1=职业,2综合")
    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @Excel(name = "导引单打印", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "导引单打印标示")
    private String dyddybs;

    @Excel(name = "所属科室名称")
    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @Excel(name = "餐序")
    @ApiModelProperty(value = "餐序")
    private String cx;

    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=通用")
    @ApiModelProperty(value = "性别")
    private Integer xb;

    @Excel(name = "价格")
    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @Excel(name = "销售打印分类")
    @ApiModelProperty(value = "销售打印分类")
    private String xsdyfl;

    @Excel(name = "外送机构")
    @ApiModelProperty(value = "外送机构ID")
    private String idCooporg;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "app显示", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "是否在APP出现：1.是  0或null.否")
    private String examfeeitemCodex;

    @Excel(name = "禁用", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "是否禁用：1.是  0或null.否")
    private Integer isBan;

    @Excel(name = "折扣")
    @ApiModelProperty(value = "折扣")
    private Double zk;

    @Excel(name = "套餐价格")
    @ApiModelProperty(value = "套餐价格")
    private Double suiteprice;

    @Excel(name = "特需价格")
    @ApiModelProperty(value = "特需价格")
    private Double txjg;

    @Excel(name = "外宾价格")
    @ApiModelProperty(value = "外宾价格")
    private Double wbjg;

    @Excel(name = "优待价格")
    @ApiModelProperty(value = "优待价格")
    private Double ydjg;

    @Excel(name = "内部价")
    @ApiModelProperty(value = "内部价")
    private Double nbj;

    @Excel(name = "外部价")
    @ApiModelProperty(value = "外部价")
    private Double coopprice;

    @Excel(name = "样本类型")
    @ApiModelProperty(value = "样本类型")
    private String yblx;

    @Excel(name = "独立标示", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "独立标示")
    private String dlbs;

    @Excel(name = "费用类型")
    @ApiModelProperty(value = "费用类型")
    private String fylx;

    @Excel(name = "收费项目打印名称")
    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @Excel(name = "报告打印标示")
    @ApiModelProperty(value = "报告打印标示")
    private String bgdybs;

    @Excel(name = "打印排列序号")
    @ApiModelProperty(value = "序号")
    private String xh;

    @Excel(name = "标示")
    @ApiModelProperty(value = "标示")
    private String bs;

    @Excel(name = "检查次数")
    @ApiModelProperty(value = "检查次数")
    private Integer jccs;

    @Excel(name = "检查意义")
    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "分中心IDs")
    private String fzxIds;
}

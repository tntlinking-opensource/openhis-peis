package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康检查结果汇总表 (按单位) 分页查询返回数据
 */
@Data
public class ComponyQueryVo implements Serializable {
    private static final long serialVersionUID = -4581138437669941627L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "用人单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "上岗前体检-体检人数")
    @ApiModelProperty(value = "上岗前体检-体检人数")
    private String sgq;

    @Excel(name = "上岗前体检-禁忌症人数")
    @ApiModelProperty(value = "上岗前体检-禁忌症人数")
    private String sgqJjz;

    @Excel(name = "在岗-体检人数")
    @ApiModelProperty(value = "在岗-体检人数")
    private String zg;

    @Excel(name = "在岗-禁忌症人数")
    @ApiModelProperty(value = "在岗-禁忌症人数")
    private String zgJjz;

    @Excel(name = "在岗-可疑职业病人数")
    @ApiModelProperty(value = "在岗-可疑职业病人数")
    private String zgZyb;

    @Excel(name = "在岗-需要观察复查人数")
    @ApiModelProperty(value = "在岗-需要观察复查人数")
    private String zgFc;

    @Excel(name = "离岗-体检人数")
    @ApiModelProperty(value = "离岗-体检人数")
    private String lg;

    @Excel(name = "离岗-可疑职业病人数")
    @ApiModelProperty(value = "离岗-可疑职业病人数")
    private String lgZyb;

    @Excel(name = "离岗-需要观察复查人数")
    @ApiModelProperty(value = "离岗-需要观察复查人数")
    private String lgFc;

    @Excel(name = "应急体检-体检人数")
    @ApiModelProperty(value = "应急体检-体检人数")
    private String yj;

    @Excel(name = "应急体检-可疑职业病人数")
    @ApiModelProperty(value = "应急体检-可疑职业病人数")
    private String yjZyb;

    @Excel(name = "应急体检-需要观察复查人数")
    @ApiModelProperty(value = "应急体检-需要观察复查人数")
    private String yjFc;

    @Excel(name = "离岗随访-体检人数")
    @ApiModelProperty(value = "离岗随访-体检人数")
    private String lgsf;

    @Excel(name = "离岗随访-可疑职业病人数")
    @ApiModelProperty(value = "离岗随访-可疑职业病人数")
    private String lgsfZyb;

    @Excel(name = "离岗随访-需要观察复查人数")
    @ApiModelProperty(value = "离岗随访-需要观察复查人数")
    private String lgsfFc;
}

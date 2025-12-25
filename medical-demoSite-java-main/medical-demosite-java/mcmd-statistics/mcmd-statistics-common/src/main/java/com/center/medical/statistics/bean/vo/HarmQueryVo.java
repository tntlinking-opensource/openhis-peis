package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业健康检查结果汇总表 (按危害因素) 返回数据
 */
@Data
public class HarmQueryVo implements Serializable {
    private static final long serialVersionUID = 9180251419986857597L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "危害因素类别")
    @ApiModelProperty(value = "harmClass")
    private Object harmClass;

    @Excel(name = "上岗前体检-体检人数")
    @ApiModelProperty(value = "sgq")
    private Object sgq;

    @Excel(name = "上岗前体检-禁忌症人数")
    @ApiModelProperty(value = "sgq_jjz")
    private Object sgq_jjz;

    @Excel(name = "在岗体检-体检人数")
    @ApiModelProperty(value = "zg")
    private Object zg;

    @Excel(name = "在岗体检-禁忌症人数")
    @ApiModelProperty(value = "zg_jjz")
    private Object zg_jjz;

    @Excel(name = "在岗体检-可疑职业病人数")
    @ApiModelProperty(value = "zg_zyb")
    private Object zg_zyb;

    @Excel(name = "在岗体检-需要观察复查人数")
    @ApiModelProperty(value = "zg_fc")
    private Object zg_fc;

    @Excel(name = "离岗体检-体检人数")
    @ApiModelProperty(value = "lg")
    private Object lg;

    @Excel(name = "离岗体检-可疑职业病人数")
    @ApiModelProperty(value = "lg_zyb")
    private Object lg_zyb;

    @Excel(name = "离岗体检-需要观察复查人数")
    @ApiModelProperty(value = "lg_fc")
    private Object lg_fc;

    @Excel(name = "应急体检-体检人数")
    @ApiModelProperty(value = "yj")
    private Object yj;

    @Excel(name = "应急体检-可疑职业病人数")
    @ApiModelProperty(value = "yj_zyb")
    private Object yj_zyb;

    @Excel(name = "应急体检-需要观察复查人数 ")
    @ApiModelProperty(value = "yj_fc")
    private Object yj_fc;

    @Excel(name = "离岗随访-体检人数")
    @ApiModelProperty(value = "lgsf")
    private Object lgsf;

    @Excel(name = "离岗随访-可疑职业病人数")
    @ApiModelProperty(value = "lgsf_zyb")
    private Object lgsf_zyb;

    @Excel(name = "离岗随访-需要观察复查人数")
    @ApiModelProperty(value = "lgsf_fc")
    private Object lgsf_fc;
}

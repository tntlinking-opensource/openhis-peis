package com.center.medical.abteilung.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 危机值提报分页参数
 */
@Data
public class CrisisValueParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5150738583335384611L;


    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "提报科室")
    private String reportDivision;

    @ApiModelProperty(value = "危急值级别")
    private String wjzjb;

    @ApiModelProperty(value = "体检分类 0个检，1团检")
    private String useCodeHiden;

    @ApiModelProperty(value = "开单医师")
    private String idOpendoctor;

    @ApiModelProperty(value = "公司")
    private String idOrg;

    @ApiModelProperty(value = "是否是医师，排序使用")
    private String isdoc;

    @ApiModelProperty(value = "回访处理人")
    private String hfclr;

}

package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class getTjtcAndItemVo implements Serializable {
    private static final long serialVersionUID = -8207736786570335567L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别 0男1女其他通用")
    private String xb;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型 0健康1职业其他综合")
    private String tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @ApiModelProperty(value = "xmzt")
    private String xmzt;

    @ApiModelProperty(value = "打印项目分类名称")
    private String printType;

    @ApiModelProperty(value = "顺序 实际使用此字段排序")
    private Integer printShunxu;


    @ApiModelProperty(value = "套餐id")
    private String tcid;
}

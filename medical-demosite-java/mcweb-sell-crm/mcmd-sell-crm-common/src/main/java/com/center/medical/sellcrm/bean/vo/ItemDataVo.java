package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class ItemDataVo implements Serializable {
    private static final long serialVersionUID = 6943501050870497547L;

    @ApiModelProperty(value = "yhj")
    private String yhj;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "mealId")
    private String mealId;

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "性别0男、1女、其他是通用")
    private String xb;

    @ApiModelProperty(value = "价格")
    private Double jg;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "体检类型：0.非职业 1.职业 其他是综合")
    private Integer tjlx;

    @ApiModelProperty(value = "所属科室名称")
    private String ssks;

    @ApiModelProperty(value = "是否备选：0.否 1.是")
    private String sfbx;

    @ApiModelProperty(value = "xmzt")
    private String xmzt;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "销售打印分类")
    private String printType;

    @ApiModelProperty(value = "打印顺序")
    private String printShunxu;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;
}

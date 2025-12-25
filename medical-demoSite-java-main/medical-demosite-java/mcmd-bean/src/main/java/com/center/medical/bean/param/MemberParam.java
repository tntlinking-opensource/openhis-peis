package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-24 10:10
 * @description: 会员档案查询参数
 */
@Data
public class MemberParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 4795804815343584207L;

    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "分中心")
    private String fzx;
    
    @ApiParam(hidden = true)
    @ApiModelProperty(value = "limit参数1")
    private Long offset;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "limit参数2")
    private Long limit;

}

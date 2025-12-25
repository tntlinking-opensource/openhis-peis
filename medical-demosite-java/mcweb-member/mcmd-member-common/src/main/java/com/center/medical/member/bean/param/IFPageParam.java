package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员管理-沟通记录分页参数
 */
@Data
public class IFPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4739861209597844964L;


    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "跟进内容")
    private String text;
}

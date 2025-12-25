package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送管理-外送结果上传分页数据
 */
@Data
public class SendGovernParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 5568792535772049444L;

    @ApiModelProperty(value = "体检号（外送结果上传使用）")
    private String patientcode;

    @ApiModelProperty(value = "姓名（外送结果上传使用）")
    private String patientname;
}

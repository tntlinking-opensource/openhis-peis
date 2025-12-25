package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 未检项目查询 分页参数
 *
 */
@Data
public class FinishStatusParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 2197931248208563499L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

}

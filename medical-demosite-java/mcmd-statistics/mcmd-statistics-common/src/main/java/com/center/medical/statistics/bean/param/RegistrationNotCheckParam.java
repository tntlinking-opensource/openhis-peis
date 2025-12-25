package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记未检客户统计 分页参数
 */
@Data
public class RegistrationNotCheckParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 610753638328257017L;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "姓名")
    private String patientname;

}

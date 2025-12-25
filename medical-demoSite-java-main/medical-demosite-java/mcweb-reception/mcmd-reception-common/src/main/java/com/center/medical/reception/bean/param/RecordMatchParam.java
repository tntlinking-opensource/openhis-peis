package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增外送登记结果处理-查询参数
 */
@Data
public class RecordMatchParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 6177087326906732386L;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "体检号")
    private String patientCode;


    @ApiModelProperty(value = "是否跳过校验，1是,其他否")
    private String skip;


}

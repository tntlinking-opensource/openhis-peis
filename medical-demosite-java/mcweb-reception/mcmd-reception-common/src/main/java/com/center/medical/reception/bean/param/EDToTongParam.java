package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当日所有检查统收的统计参数
 */
@Data
public class EDToTongParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 1553311270423091534L;

    @ApiModelProperty(value = "机构名称")
    private String orgName;


    public EDToTongParam(String orgName) {
        this.orgName = orgName;
    }

    public EDToTongParam() {
    }
}

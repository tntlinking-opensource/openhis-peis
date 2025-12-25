package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费日报-每日记账报表统计参数
 */
@Data
public class EveryDayJZParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 4885889529739882291L;

    @ApiModelProperty(value = "客户单位名称id")
    private String khdwmcid;

    @ApiModelProperty(value = "姓名输入码")
    private String inputCode;

}

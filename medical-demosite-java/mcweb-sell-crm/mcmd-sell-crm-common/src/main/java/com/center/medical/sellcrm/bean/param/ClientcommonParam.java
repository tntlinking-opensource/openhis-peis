package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-19 9:01
 * @description: 客户公共池查询参数
 */
@Data
public class ClientcommonParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 4278239574554104952L;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

}

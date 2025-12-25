package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-11-22 11:56
 * @description: 销售合同查询条件
 */
@Data
public class SellpactParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -3959846213205763723L;

    @ApiModelProperty(value = "合同名称")
    private String htmc;

    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "合同签订日期")
    private Date htqdrq;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;
}

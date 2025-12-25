package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 应收预收函证 分页参数
 */
@Data
public class ReceiptLetterParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 5238911423513866872L;


    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;

}

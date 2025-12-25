package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/5/26 14:52
 * @description: 复查卡额度查询结果
 */
@Data
public class RecheckVo implements Serializable {
    private static final long serialVersionUID = -7801808313023945384L;

    @ApiModelProperty(value = "卡ID")
    private String id;

    @ApiModelProperty(value = "复查额度（体检时间一年后到期（判断套餐登记日期，必须要有赠送套餐）  按钮手动扣除）")
    private Double recheckMoney;

    @ApiModelProperty(value = "卡说明")
    private String cardState;
}

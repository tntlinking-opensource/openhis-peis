package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.enums.CustomerStatus;
import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-11-19 14:24
 * @description: 销售客户查询参数
 */
@Data
public class SellcustomerParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -7879917641657052660L;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "团体ID", notes = "客户单位团体号")
    private String intId;

    /**
     * @see CustomerStatus
     */
    @ApiModelProperty(value = "客户状态：0潜在 1正式 2释放")
    private Integer khzt;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;
}
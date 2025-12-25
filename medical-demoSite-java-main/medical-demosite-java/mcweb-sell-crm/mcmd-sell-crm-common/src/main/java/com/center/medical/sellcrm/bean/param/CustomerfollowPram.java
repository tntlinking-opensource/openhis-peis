package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.enums.CustomerFollowStatus;
import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-21 9:58
 * @description: 客户跟进查询参数
 */
@Data
public class CustomerfollowPram extends BaseParam implements Serializable {

    private static final long serialVersionUID = 1847604391642682349L;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "销售经理")
    private String xsjlid;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位ID")
    private String khdwmcid;

    /**
     * @see CustomerFollowStatus
     */
    @ApiModelProperty(value = "跟进阶段：0.需求交流 1.方案谈判 2.价格谈判 3.体检确认 4.合同签订")
    private Integer gjjd;

}
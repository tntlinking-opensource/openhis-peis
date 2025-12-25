package com.center.medical.finance.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡管理-卡消费明细分页参数
 */
@Data
public class SDPageParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 1186983321966754298L;

    @ApiModelProperty(value = "分中心")
    private String branchCenter;

    @ApiModelProperty(value = "是否为增加：0.为充值 1.为消费")
    private Integer isAdd;

    @ApiModelProperty(value = "会员卡号")
    private String cardId;

    @ApiModelProperty(value = "消费类型，：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品")
    private Integer consumetype;

}

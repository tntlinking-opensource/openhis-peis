package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-11-21 15:55
 * @description: 普通套餐查询参数
 */
@Data
public class CreatemealParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = 4949542802719918314L;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "体检类型 0健康体检，1职业体检，2综合体检")
    private Integer tjlx;


    @ApiModelProperty(value = "适用性别：0.男 1.女 2.通用")
    private Integer syxb;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "输入码")
    private String userNo;


    @ApiModelProperty(value = "按照客户名称排序 1是 null否")
    private Integer customerNameSort;

    @ApiModelProperty(value = "下级id")
    private List<String> lowerLevelIds;

    @ApiModelProperty(value = "平安id")
    private String pinganId;
}

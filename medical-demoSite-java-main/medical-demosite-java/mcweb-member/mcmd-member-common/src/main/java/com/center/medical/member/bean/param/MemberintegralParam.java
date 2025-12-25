package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-24 11:33
 * @description: 会员积分数据查询参数
 */
@Data
public class MemberintegralParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5615915842291405722L;

    @ApiModelProperty(value = "会员卡号")
    private String cardId;

    @ApiModelProperty(value = "会员姓名")
    private String memberName;

    @ApiModelProperty(value = "卡类型")
    private String cardType;

    @ApiModelProperty(value = "是否为增加：0或null.否 1.是")
    private Integer isAdd;
}

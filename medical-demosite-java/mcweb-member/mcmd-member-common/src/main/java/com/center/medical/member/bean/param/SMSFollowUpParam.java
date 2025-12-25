package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约短信回访
 */

@Data
public class SMSFollowUpParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4717120059301363378L;

    @ApiModelProperty(value = "通知状态：0.未通知 1.已通知 2.等待通知 3.通知失败")
    private Integer notifyResult;

    @ApiModelProperty(value = "体检分类 0个检，1团检")
    private String useCodeHiden;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "type,0体检号为空，1不为空")
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "isReg,1显示开单医师是当前用户")
    private Integer isReg;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "今天的开始时间")
    private Date beginDay;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "今天的结束时间")
    private Date endDay;
}

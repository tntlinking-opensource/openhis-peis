package com.center.medical.reception.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 取得已预约客户参数
 */
@Data
public class ReservationUserParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -5121225199619023656L;

    @ApiModelProperty(value = "登记页面显示开单医师是当前用户的记录，传1")
    private Integer isReg;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "通知结果状态，0.通知失败 1.已取消 2.等待通知 3.已通知")
    private Integer notifyResult;

    @ApiModelProperty(value = "体检分类 0个检，1团检")
    private String useCodeHiden;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "今天的开始时间")
    private Date dayStart;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "今天的结束时间")
    private Date dayEnd;
}

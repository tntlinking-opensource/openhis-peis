package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检预约可用人数 返回数据
 */
@Data
public class PingAnNumsVo implements Serializable {
    private static final long serialVersionUID = 9055130370464274531L;

    @ApiModelProperty(value = "具体每一天的日期,yyyyMMdd")
    private String date;

    @ApiModelProperty(value = "是否可预约：作为机构某一天营业（放假）的一个总开关 可预约1 不可预约0")
    private Integer canOrder;

    @ApiModelProperty(value = "是否提供 VIP 预约数据：提供：1 不提供：0")
    private Integer provideVipData;

    @ApiModelProperty(value = "VIP 最大可体检人数")
    private Integer vipMaxNum;

    @ApiModelProperty(value = "VIP 已预约人数")
    private Integer vipOrderNum;

    @ApiModelProperty(value = "最大可体检人数（包括 vip 和非 vip）")
    private Integer maxNum;

    @ApiModelProperty(value = "已预约人数（包括 vip 和非 vip）")
    private Integer orderNum;
}

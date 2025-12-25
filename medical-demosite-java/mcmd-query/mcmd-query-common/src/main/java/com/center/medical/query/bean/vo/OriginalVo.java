package com.center.medical.query.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室拒检查询 分页返回数据
 */
@Data
public class OriginalVo implements Serializable {
    private static final long serialVersionUID = -1453636328445413948L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "综述")
    private String summarize;

}

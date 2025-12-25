package com.center.medical.statistics.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室工作量 分页参数
 */
@Data
public class DivisionWorkParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -517234941178501481L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime2;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime2;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "录入人")
    private String writename;

    @ApiModelProperty(value = "0.未审核 1.已审核")
    private Integer status;

    @ApiModelProperty(value = "科室")
    private String ks;

    @ApiModelProperty(value = "团体名称")
    private String idOrg;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "审核人")
    private String auditName;

}

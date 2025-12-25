package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约来检情况分析 导出参数
 */
@Data
public class RAExportParam implements Serializable {
    private static final long serialVersionUID = 2304290556598864617L;

    @ApiModelProperty(value = "分中心id（单选）")
    private String branchId;


    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;


    @ApiModelProperty(value = "团体ID")
    private String intId;


    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;


    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "类型：1已预约来检、2已预约未检、3未预约体检人数")
    private Integer type;
}

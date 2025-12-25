package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 预约设置批量修改日期及人员类型
 */
@Data
public class BatchModifyParam implements Serializable {
    private static final long serialVersionUID = -1727252891813913967L;


    @ApiModelProperty(value = "预约时间")
    private Date reservationDate;

    @ApiModelProperty(value = "人员类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "人员类型名称")
    private String levelName;

    @ApiModelProperty(value = "要修改的数据id,多条")
    private List<String> ids;

    @ApiModelProperty(value = "分中心ID")
    @NotBlank(message = "请先选择预约的分中心!")
    private String branchId;

}

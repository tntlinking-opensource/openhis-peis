package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日体检项目统计 分页返回数据
 */
@Data
public class EveryProjectVo implements Serializable {
    private static final long serialVersionUID = -1072127114381417668L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="检查日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "every0")
    private String every0;

    @Excel(name = "科室")
    @ApiModelProperty(value = "every1")
    private String every1;

    @Excel(name = "体检项目")
    @ApiModelProperty(value = "every2")
    private String every2;

    @Excel(name = "医生")
    @ApiModelProperty(value = "every3")
    private String every3;

    @Excel(name = "体检者数量")
    @ApiModelProperty(value = "every4")
    private String every4;

    @Excel(name = "体检者数量(个人)")
    @ApiModelProperty(value = "every5")
    private String every5;

    @Excel(name = "体检者数量(团体)")
    @ApiModelProperty(value = "every6")
    private String every6;

    @ApiModelProperty(value = "every7")
    private Integer every7;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "收费项目id")
    private String itemId;


}

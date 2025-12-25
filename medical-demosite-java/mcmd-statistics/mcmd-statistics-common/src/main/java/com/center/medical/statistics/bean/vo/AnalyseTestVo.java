package com.center.medical.statistics.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验科科室医生工作量统计 返回数据
 */
@Data
public class AnalyseTestVo implements Serializable {
    private static final long serialVersionUID = 5054788755178612826L;


    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty(value = "体检套餐名称")
    private String examName;

    @ApiModelProperty(value = "工作量")
    private Integer gzlTotal;

    @ApiModelProperty(value = "工作量-个人")
    private Integer gzlGr;

    @ApiModelProperty(value = "工作量-团体")
    private Integer gzlTt;

    @ApiModelProperty(value = "星期")
    private Integer dayForWeek;


}

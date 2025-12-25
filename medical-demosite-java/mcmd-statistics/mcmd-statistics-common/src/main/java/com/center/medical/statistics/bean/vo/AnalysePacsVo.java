package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * pacs科室医生工作量统计
 */
@Data
public class AnalysePacsVo implements Serializable {
    private static final long serialVersionUID = -6100981807355814487L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="检查时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;

    @Excel(name = "星期")
    @ApiModelProperty(value = "星期")
    private Integer dayForWeek;

    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String depName;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "体检套餐名称")
    private String examName;

    @Excel(name = "医生")
    @ApiModelProperty("用户名称")
    private String username;

    @Excel(name = "工作量")
    @ApiModelProperty(value = "工作量")
    private Integer gzlTotal;

    @ApiModelProperty(value = "工作量-个人")
    private Integer gzlGr;

    @ApiModelProperty(value = "工作量-团体")
    private Integer gzlTt;

    @ApiModelProperty(value = "原始金额")
    private BigDecimal oriTotal;

    @ApiModelProperty(value = "原始金额-个人")
    private BigDecimal oriGr;

    @ApiModelProperty(value = "原始金额-团体")
    private BigDecimal oriTt;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal yhTotal;

    @ApiModelProperty(value = "优惠金额-个人")
    private BigDecimal yhGr;

    @ApiModelProperty(value = "优惠金额-团体")
    private BigDecimal yhTt;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("vip")
    private String vip;

    @ApiModelProperty("vvip")
    private String vvip;

    @ApiModelProperty("common")
    private String common;
}

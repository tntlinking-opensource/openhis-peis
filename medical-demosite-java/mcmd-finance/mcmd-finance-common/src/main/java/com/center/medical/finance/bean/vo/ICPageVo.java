package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客服销售统计 分页 返回数据
 */
@Data
public class ICPageVo implements Serializable {
    private static final long serialVersionUID = 6188849174372064095L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "经理名称")
    private String jlmc;

    @Excel(name = "上次团体开单医生")
    @ApiModelProperty(value = "上次团体开单医生")
    private String lastJlmc;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String tjh;

    @Excel(name = "体检人员名称")
    @ApiModelProperty(value = "体检人员名称")
    private String tjrymc;

    @Excel(name = "可算提成合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "可算提成合计")
    private String kstchj;

    @Excel(name = "实付合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实付")
    private String sf;

    @Excel(name = "不可算提成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "不可算提成")
    private String bkstc;

    @Excel(name = "加项优惠价合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "加项优惠价合计")
    private String jxyhjhj;

    @Excel(name = "实际提成数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实际提成数")
    private String sjtcs;

    @Excel(name = "登记时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date djrq;

    @Excel(name = "套餐名称")
    @ApiModelProperty(value = "套餐名称")
    private String tcmc;


}

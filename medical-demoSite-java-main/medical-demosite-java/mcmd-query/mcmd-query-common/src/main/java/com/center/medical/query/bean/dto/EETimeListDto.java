package com.center.medical.query.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 统计某段时间所有体检者
 */
@Data
public class EETimeListDto implements Serializable {
    private static final long serialVersionUID = -3627250816759082767L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;


    @Excel(name = "体检团体")
    @ApiModelProperty(value = "tjtt")
    private String tjtt;

    @Excel(name = "健康人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jkrs")
    private String jkrs;

    @Excel(name = "职业人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "zyrs")
    private String zyrs;

    @Excel(name = "综合人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "zhrs")
    private String zhrs;

    @Excel(name = "复查人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "fcrs")
    private String fcrs;

    @Excel(name = "原价合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "yjhj")
    private String yjhj;

    @Excel(name = "应付合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "金额应付")
    private BigDecimal yfhj;

    @Excel(name = "记账未结", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "记账未结")
    private String jzwj;

    @Excel(name = "折扣率")
    @ApiModelProperty(value = "折扣率")
    private String zkl;

    @Excel(name = "加项人次", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jxrc")
    private String jxrc;

    @Excel(name = "加项费用", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jxfy")
    private String jxfy;


    @ApiModelProperty(value = "lbrs")
    private String lbrs;

    @Excel(name = "已登记", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "ydj")
    private BigDecimal ydj;

    @Excel(name = "未登记", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "wdj")
    private String wdj;

    @Excel(name = "客单价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "价格")
    private String unitprice;

    @ApiModelProperty(value = "yjs")
    private String yjs;

    @ApiModelProperty(value = "wjs")
    private String wjs;

    @Excel(name = "男", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "男")
    private String nan;

    @Excel(name = "女", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "女")
    private String nv;

    @Excel(name = "分检完成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "ffwc")
    private String ffwc;

    @Excel(name = "分检未完成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "fjww")
    private String fjww;

    @Excel(name = "健康总检完成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jkwc")
    private String jkwc;

    @Excel(name = "健康总检未完成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jkww")
    private String jkww;

    @Excel(name = "职业总检完成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "zywc")
    private String zywc;

    @Excel(name = "职业总检未完成", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "zyww")
    private String zyww;

    @Excel(name = "健康报告已打印", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jkbgyd")
    private String jkbgyd;

    @Excel(name = "健康报告未打印", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "jkbgwd")
    private String jkbgwd;

    @Excel(name = "职业报告已打印", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "zybgyd")
    private String zybgyd;

    @Excel(name = "职业报告未打印", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "zybgwd")
    private String zybgwd;


    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;
}

package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出工作量统计总计
 */
@Data
public class ExportTotalVo implements Serializable {
    private static final long serialVersionUID = 6581561616552862573L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

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
}

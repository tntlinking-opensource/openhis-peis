package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出(金蝶名单)
 */
@Data
public class ExportKingdeeNameVo implements Serializable {
    private static final long serialVersionUID = 2076064367073061249L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "金蝶单位名称")
    @ApiModelProperty(value = "金蝶单位名称")
    private String jindieId;

    @Excel(name = "客户单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private Integer intId;

    @Excel(name = "客户经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "在本中心系统里的名字")
    @ApiModelProperty(value = "销售经理名称")
    private String userName;

    @Excel(name = "金蝶账户")
    @ApiModelProperty(value = "金蝶账户")
    private String accountNo;
}

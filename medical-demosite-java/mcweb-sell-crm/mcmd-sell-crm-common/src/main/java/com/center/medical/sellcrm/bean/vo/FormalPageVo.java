package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 正式客户分页 返回数据
 */
@Data
public class FormalPageVo implements Serializable {
    private static final long serialVersionUID = 2297501772718619154L;

    @Excel(name = "客户单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "客户单位输入码")
    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "客户单位团体号")
    private String intId;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "备单时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "备单日期")
    private Date bdrq;

}

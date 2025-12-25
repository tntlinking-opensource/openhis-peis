package com.center.medical.report.bean.model;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExportStatistics implements Serializable {

    private static final long serialVersionUID = -3719951579351679078L;

    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "交接状态")
    @ApiModelProperty(value = "体检状态：-2检验报告,-1报告上传时生成的,0:总检开始、1:总检完成、2:报告已打印、3:报告一审通过、4:报告一审不通过、5:二审通过、6:二审不通过、7:终审通过、8:终审不通过、9:报告已交接、10:报告已通知、11:报告已领取")
    private String status;

    @Excel(name = "公司")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "交接份数（团）")
    @ApiModelProperty(value = "交接份数（团）")
    private String orgcount;

    @Excel(name = "交接份数（个）")
    @ApiModelProperty(value = "交接份数（个）")
    private String personalcount;

    @Excel(name = "交接人")
    @ApiModelProperty(value = "交出人名称")
    private String joinPersonMan;

    @Excel(name = "承接人")
    @ApiModelProperty(value = "接受人名称")
    private String revPersonMan;

    @Excel(name = "交接时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "接受时间")
    private Date revTime;

    @Excel(name = "柜子号")
    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String doctorapply;

    @Excel(name = "发放方式")
    @ApiModelProperty(value = "发放方式")
    private String methodName;



}

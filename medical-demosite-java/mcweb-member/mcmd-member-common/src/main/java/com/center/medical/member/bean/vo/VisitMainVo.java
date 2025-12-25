package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验样本-不合格样本分页数据
 */
@Data
public class VisitMainVo implements Serializable {
    private static final long serialVersionUID = -4947353968925790573L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @Excel(name = "样本不合格原因")
    @ApiModelProperty(value = "样本不合格原因ID")
    private String belowquestion;

    @Excel(name = "创建日期")
    @ApiModelProperty(value = "创建日期")
    private String createDate;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;


    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "总检状态：-1.总检未开始,0.总检开始、1.总检完成、2.报告已打印、3.报告一审通过、4.报告一审不通过、" +
            "5.二审通过、6.二审不通过、7.终审通过、8.终审不通过、9.报告已交接、10.报告已通知、11.报告已领取")
    private Integer jktjzt;

    @Excel(name = "是否处理" ,readConverterExp = "null=否,0=否,1=是")
    @ApiModelProperty(value = "是否处理")
    private Integer isHandled;

}

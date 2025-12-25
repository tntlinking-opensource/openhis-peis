package com.center.medical.member.bean.vo;

import com.center.medical.bean.model.AdvanceVisitWrite;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.Peispatientarchive;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CeShiVO implements Serializable {
    private static final long serialVersionUID = -1982461279258479016L;


    @ApiModelProperty(value = "体检者档案表")
    private Peispatientarchive peispatientarchive;

    @ApiModelProperty(value = "主表记录体检者是否来检")
    private AdvanceVisitWrite advanceVisitWrite;

    @ApiModelProperty(value = "体检者表")
    private Peispatient peispatient;

    @ApiModelProperty(value = "体检者费用主表")
    private PeispatientChargeMain peispatientChargeMain;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "支付方式")
    private String payways;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "结论")
    private String signList;

}

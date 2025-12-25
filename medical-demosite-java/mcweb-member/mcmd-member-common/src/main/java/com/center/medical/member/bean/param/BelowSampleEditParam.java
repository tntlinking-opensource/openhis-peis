package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 不合格样本回访
 */
@Data
public class BelowSampleEditParam implements Serializable {

    private static final long serialVersionUID = -1922951717247098501L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "性别id")
    private String idSex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "体检时间")
    private Date dateregister;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "样本不合格原因ID")
    private String belowquestion;

    @ApiModelProperty(value = "回访时间")
    private Date visitTime;

    @ApiModelProperty(value = "再通知")
    private Integer noticeAgain;

    @ApiModelProperty(value = "回访备注")
    private String memo;


}

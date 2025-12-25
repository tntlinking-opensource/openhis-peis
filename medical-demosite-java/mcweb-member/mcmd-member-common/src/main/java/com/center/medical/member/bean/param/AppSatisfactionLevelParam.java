package com.center.medical.member.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序体检满意度参数
 */
@Data
public class AppSatisfactionLevelParam implements Serializable {
    private static final long serialVersionUID = 8377897285176618634L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "个检/团检：0.个检 1.团检")
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "问题1")
    private String question1;

    @ApiModelProperty(value = "问题2")
    private String question2;

    @ApiModelProperty(value = "问题3")
    private String question3;

    @ApiModelProperty(value = "问题4")
    private String question4;

    @ApiModelProperty(value = "问题5")
    private String question5;

    @ApiModelProperty(value = "问题6")
    private String question6;


    @ApiModelProperty(value = "评价时间-开始")
    private Date startTime;

    @ApiModelProperty(value = "评价时间-结束")
    private Date endTime;

    @ApiModelProperty(value = "登记时间-开始")
    private Date registrationStartTime;

    @ApiModelProperty(value = "登记时间-结束")
    private Date registrationEndTime;

}

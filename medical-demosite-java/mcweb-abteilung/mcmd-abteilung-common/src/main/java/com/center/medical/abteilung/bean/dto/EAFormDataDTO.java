package com.center.medical.abteilung.bean.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 电测听保存更新数据（电测听数据）
 */
@Data
public class EAFormDataDTO implements Serializable {
    private static final long serialVersionUID = 8075074310452781942L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "气导左耳125Hz")
    private Double airLeft125;

    @ApiModelProperty(value = "气导右耳125Hz")
    private Double airRight125;

    @ApiModelProperty(value = "气导左耳250Hz")
    private Double airLeft250;

    @ApiModelProperty(value = "气导右耳250Hz")
    private Double airRight250;

    @ApiModelProperty(value = "气导左耳500Hz")
    private Double airLeft500;

    @ApiModelProperty(value = "气导右耳500Hz")
    private Double airRight500;

    @ApiModelProperty(value = "气导左耳1000Hz")
    private Double airLeft1000;

    @ApiModelProperty(value = "气导右耳1000Hz")
    private Double airRight1000;

    @ApiModelProperty(value = "气导左耳2000Hz")
    private Double airLeft2000;

    @ApiModelProperty(value = "气导右耳2000Hz")
    private Double airRight2000;

    @ApiModelProperty(value = "气导左耳3000Hz")
    private Double airLeft3000;

    @ApiModelProperty(value = "气导右耳3000Hz")
    private Double airRight3000;

    @ApiModelProperty(value = "气导左耳4000Hz")
    private Double airLeft4000;

    @ApiModelProperty(value = "气导右耳4000Hz")
    private Double airRight4000;

    @ApiModelProperty(value = "气导左耳6000Hz")
    private Double airLeft6000;

    @ApiModelProperty(value = "气导右耳6000Hz")
    private Double airRight6000;

    @ApiModelProperty(value = "气导左耳8000Hz")
    private Double airLeft8000;

    @ApiModelProperty(value = "气导右耳8000Hz")
    private Double airRight8000;


    @ApiModelProperty(value = "骨导左耳250Hz")
    private Double boneLeft250;

    @ApiModelProperty(value = "骨导右耳250Hz")
    private Double boneRight250;

    @ApiModelProperty(value = "骨导左耳500Hz")
    private Double boneLeft500;

    @ApiModelProperty(value = "骨导右耳500Hz")
    private Double boneRight500;

    @ApiModelProperty(value = "骨导左耳1000Hz")
    private Double boneLeft1000;

    @ApiModelProperty(value = "骨导右耳1000Hz")
    private Double boneRight1000;

    @ApiModelProperty(value = "骨导左耳2000Hz")
    private Double boneLeft2000;

    @ApiModelProperty(value = "骨导右耳2000Hz")
    private Double boneRight2000;

    @ApiModelProperty(value = "骨导左耳3000Hz")
    private Double boneLeft3000;

    @ApiModelProperty(value = "骨导右耳3000Hz2")
    private Double boneRight3000;

    @ApiModelProperty(value = "骨导左耳4000Hz")
    private Double boneLeft4000;

    @ApiModelProperty(value = "骨导右耳4000Hz")
    private Double boneRight4000;

    @ApiModelProperty(value = "骨导左耳6000Hz")
    private Double boneLeft6000;

    @ApiModelProperty(value = "骨导右耳6000Hz")
    private Double boneRight6000;

    @ApiModelProperty(value = "骨导左耳8000Hz")
    private Double boneLeft8000;

    @ApiModelProperty(value = "骨导右耳8000Hz")
    private Double boneRight8000;

    @TableField(value="`describe`")
    @ApiModelProperty(value = "备注说明")
    private String describe;

    @ApiModelProperty(value = "结果评定")
    private String testResult;
}

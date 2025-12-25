package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * KS电测听(MdElectroAudiometer)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
@Data
@TableName("md_electro_audiometer")
@ApiModel(value = "MdElectroAudiometer", description = "KS电测听实体类")
public class MdElectroAudiometer extends Model<MdElectroAudiometer> implements Serializable {
    private static final long serialVersionUID = -51487836382828833L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "体检类型ID：0.健康体检 1.职业体检 2.综合")
    private String idExamtype;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "骨导左耳250Hz")
    private String boneLeft250;

    @ApiModelProperty(value = "气导左耳125Hz")
    private String airLeft125;

    @ApiModelProperty(value = "气导右耳125Hz")
    private String airRight125;

    @ApiModelProperty(value = "骨导右耳250Hz")
    private String boneRight250;

    @ApiModelProperty(value = "骨导左耳4000Hz")
    private String boneLeft4000;

    @ApiModelProperty(value = "骨导右耳4000Hz")
    private String boneRight4000;

    @ApiModelProperty(value = "骨导左耳2000Hz")
    private String boneLeft2000;

    @ApiModelProperty(value = "骨导右耳2000Hz")
    private String boneRight2000;

    @ApiModelProperty(value = "骨导左耳1000Hz")
    private String boneLeft1000;

    @ApiModelProperty(value = "骨导右耳1000Hz")
    private String boneRight1000;

    @ApiModelProperty(value = "骨导左耳500Hz")
    private String boneLeft500;

    @ApiModelProperty(value = "骨导右耳500Hz")
    private String boneRight500;

    @ApiModelProperty(value = "气导左耳250Hz")
    private String airLeft250;

    @ApiModelProperty(value = "气导右耳250Hz")
    private String airRight250;

    @ApiModelProperty(value = "气导左耳500Hz")
    private String airLeft500;

    @ApiModelProperty(value = "气导右耳500Hz")
    private String airRight500;

    @ApiModelProperty(value = "气导左耳1000Hz")
    private String airLeft1000;

    @ApiModelProperty(value = "气导右耳1000Hz")
    private String airRight1000;

    @ApiModelProperty(value = "气导左耳2000Hz")
    private String airLeft2000;

    @ApiModelProperty(value = "气导右耳2000Hz")
    private String airRight2000;

    @ApiModelProperty(value = "气导左耳4000Hz")
    private String airLeft4000;

    @ApiModelProperty(value = "气导右耳4000Hz")
    private String airRight4000;

    @ApiModelProperty(value = "气导左耳8000Hz")
    private String airLeft8000;

    @ApiModelProperty(value = "气导右耳8000Hz")
    private String airRight8000;

    @ApiModelProperty(value = "气导左耳3000Hz")
    private String airLeft3000;

    @ApiModelProperty(value = "气导右耳3000Hz")
    private String airRight3000;

    @ApiModelProperty(value = "气导左耳6000Hz")
    private String airLeft6000;

    @ApiModelProperty(value = "备注说明")
    private String describe;

    @ApiModelProperty(value = "结果评定")
    private String testResult;

    @ApiModelProperty(value = "骨导左耳3000Hz")
    private String boneLeft3000;

    @ApiModelProperty(value = "气导右耳6000Hz")
    private String airRight6000;

    @ApiModelProperty(value = "骨导右耳3000Hz2")
    private String boneRight3000;

    @ApiModelProperty(value = "气导折线图路径")
    private String airImgPath;

    @ApiModelProperty(value = "骨导折线图路径")
    private String boneImgPath;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;
}

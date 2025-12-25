package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS电测听(ElectroAudiometer)表实体类
 *
 * @author 路飞船长
 * @since 2022-12-09 11:43:19
 */
@Data
@TableName("md_electro_audiometer")
@ApiModel(value = "ElectroAudiometer", description = "KS电测听实体类")
public class ElectroAudiometer extends Model<ElectroAudiometer> implements Serializable {
    private static final long serialVersionUID = -50290584438995167L;

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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳125Hz")
    private Double airLeft125;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳125Hz")
    private Double airRight125;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳250Hz")
    private Double boneLeft250;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳250Hz")
    private Double boneRight250;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳4000Hz")
    private Double boneLeft4000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳4000Hz")
    private Double boneRight4000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳2000Hz")
    private Double boneLeft2000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳2000Hz")
    private Double boneRight2000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳1000Hz")
    private Double boneLeft1000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳1000Hz")
    private Double boneRight1000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳500Hz")
    private Double boneLeft500;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳500Hz")
    private Double boneRight500;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳250Hz")
    private Double airLeft250;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳250Hz")
    private Double airRight250;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳500Hz")
    private Double airLeft500;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳500Hz")
    private Double airRight500;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳1000Hz")
    private Double airLeft1000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳1000Hz")
    private Double airRight1000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳2000Hz")
    private Double airLeft2000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳2000Hz")
    private Double airRight2000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳4000Hz")
    private Double airLeft4000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳4000Hz")
    private Double airRight4000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳8000Hz")
    private Double airLeft8000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳8000Hz")
    private Double airRight8000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳3000Hz")
    private Double airLeft3000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳3000Hz")
    private Double airRight3000;

    @TableField(value="`describe`")
    @ApiModelProperty(value = "备注说明")
    private String describe;

    @ApiModelProperty(value = "结果评定")
    private String testResult;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳3000Hz")
    private Double boneLeft3000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳3000Hz2")
    private Double boneRight3000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导左耳6000Hz")
    private Double airLeft6000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "气导右耳6000Hz")
    private Double airRight6000;

    @ApiModelProperty(value = "气导折线图路径（左耳）")
    private String airImgPath;

    @ApiModelProperty(value = "骨导折线图路径（右耳）")
    private String boneImgPath;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @TableField(exist = false)
    @ApiModelProperty(value = "地址")
    private String realPath;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳6000Hz")
    private Double boneLeft6000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳6000Hz")
    private Double boneRight6000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导左耳8000Hz")
    private Double boneLeft8000;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "骨导右耳8000Hz")
    private Double boneRight8000;


}

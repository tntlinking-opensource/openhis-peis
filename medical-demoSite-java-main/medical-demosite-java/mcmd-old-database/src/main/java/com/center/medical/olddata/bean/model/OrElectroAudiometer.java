package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS电测听(ElectroAudiometer)表实体类
 *
 * @author ay
 * @since 2024-06-05 15:45:03
 */
@Data
@TableName("ELECTRO_AUDIOMETER")
@ApiModel(value = "ElectroAudiometer", description = "KS电测听实体类")
public class OrElectroAudiometer extends Model<OrElectroAudiometer> implements Serializable {
    private static final long serialVersionUID = -50501286132270108L;

    @ApiModelProperty(value = "科室ID")
    private String depId;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "职业/健康")
    private String idExamtype;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "创建日期")
    private Date createdate;


    @ApiModelProperty(value = "更新日期")
    private Date modifydate;


    @ApiModelProperty(value = "骨导左耳250Hz")
    private Integer boneLeft250;


    @ApiModelProperty(value = "气导左耳125Hz")
    private Integer airLeft125;


    @ApiModelProperty(value = "气导右耳125Hz")
    private Integer airRight125;


    @ApiModelProperty(value = "骨导右耳250Hz")
    private Integer boneRight250;


    @ApiModelProperty(value = "骨导左耳4000Hz")
    private Integer boneLeft4000;


    @ApiModelProperty(value = "骨导右耳4000Hz")
    private Integer boneRight4000;


    @ApiModelProperty(value = "骨导左耳2000Hz")
    private Integer boneLeft2000;


    @ApiModelProperty(value = "骨导右耳2000Hz")
    private Integer boneRight2000;


    @ApiModelProperty(value = "骨导左耳1000Hz")
    private Integer boneLeft1000;


    @ApiModelProperty(value = "骨导右耳1000Hz")
    private Integer boneRight1000;


    @ApiModelProperty(value = "骨导左耳500Hz")
    private Integer boneLeft500;


    @ApiModelProperty(value = "骨导右耳500Hz")
    private Integer boneRight500;


    @ApiModelProperty(value = "气导左耳250Hz")
    private Integer airLeft250;


    @ApiModelProperty(value = "气导右耳250Hz")
    private Integer airRight250;


    @ApiModelProperty(value = "气导左耳500Hz")
    private Integer airLeft500;


    @ApiModelProperty(value = "气导右耳500Hz")
    private Integer airRight500;


    @ApiModelProperty(value = "气导左耳1000Hz")
    private Integer airLeft1000;


    @ApiModelProperty(value = "气导右耳1000Hz")
    private Integer airRight1000;


    @ApiModelProperty(value = "气导左耳2000Hz")
    private Integer airLeft2000;


    @ApiModelProperty(value = "气导右耳2000Hz")
    private Integer airRight2000;


    @ApiModelProperty(value = "气导左耳4000Hz")
    private Integer airLeft4000;


    @ApiModelProperty(value = "气导右耳4000Hz")
    private Integer airRight4000;


    @ApiModelProperty(value = "气导左耳8000Hz")
    private Integer airLeft8000;


    @ApiModelProperty(value = "气导右耳8000Hz")
    private Integer airRight8000;


    @ApiModelProperty(value = "气导左耳3000Hz")
    private Integer airLeft3000;


    @ApiModelProperty(value = "气导右耳3000Hz")
    private Integer airRight3000;


    @ApiModelProperty(value = "气导左耳6000Hz")
    private Integer airLeft6000;


    @ApiModelProperty(value = "备注说明")
    private String describe;


    @ApiModelProperty(value = "结果评定")
    private String testResult;


    @ApiModelProperty(value = "骨导左耳3000Hz")
    private Integer boneLeft3000;


    @ApiModelProperty(value = "气导右耳6000Hz")
    private Integer airRight6000;


    @ApiModelProperty(value = "骨导右耳3000Hz2")
    private Integer boneRight3000;


    @ApiModelProperty(value = "气导折线图路径")
    private String airImgPath;


    @ApiModelProperty(value = "骨导折线图路径")
    private String boneImgPath;


    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

}

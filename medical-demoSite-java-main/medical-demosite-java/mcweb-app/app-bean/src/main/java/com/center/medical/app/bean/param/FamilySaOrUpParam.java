package com.center.medical.app.bean.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 家人 添加参数
 */
@Data
public class FamilySaOrUpParam implements Serializable {
    private static final long serialVersionUID = -6146737299432186998L;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "姓名")
    private String patientname;


    @ApiModelProperty(value = "身份证号")
    private String idcardno;


    @ApiModelProperty(value = "性别ID")
    private Integer idSex;


    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "客户证件类型")
    private Integer countreportoccupationxml;


}

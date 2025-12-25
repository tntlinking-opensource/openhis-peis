package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class BaseDictionaryVo implements Serializable {

    @ApiModelProperty(value = "序列")
    private String bdid;

    @ApiModelProperty(value = "科室")
    private String ks;

    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "字典代码")
    private String zddm;

    @ApiModelProperty(value = "字典名称")
    private String zdmc;

    @ApiModelProperty(value = "体检类别，0上岗前，1在岗期间,2离岗时,3离岗后,4应急")
    private String tjlb;

    @ApiModelProperty(value = "职业危害因素")
    private String whys;

    @ApiModelProperty(value = "检查结论")
    private String jcjl;


    @ApiModelProperty(value = "(MEDICAL_ID，体检系统id)")
    private String id;

    @ApiModelProperty(value = "(DICTIONARY_TYPE,字典类型编码)")
    private String classCode;

}

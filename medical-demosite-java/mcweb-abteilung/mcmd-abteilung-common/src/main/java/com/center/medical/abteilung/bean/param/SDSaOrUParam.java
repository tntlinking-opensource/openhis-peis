package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 检验样本-样本录入添加参数
 */
@Data
public class SDSaOrUParam implements Serializable {
    private static final long serialVersionUID = -6365196390555306904L;


    @ApiModelProperty(value = "項目列表id的集合")
    private List<String> ids;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别,0男1女")
    private String idSex;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;
}

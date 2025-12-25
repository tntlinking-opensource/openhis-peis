package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查看列队返回数据
 */
@Data
public class RankDataVo implements Serializable {
    private static final long serialVersionUID = 8145769139783940716L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "状态，0或空未检，1已检")
    private String status;

    @ApiModelProperty(value = "弃检项目")
    private String qjxm;

    @ApiModelProperty(value = "迟检项目")
    private String cjxm;

    @ApiModelProperty(value = "补检项目")
    private String bjxm;

    @ApiModelProperty(value = "小结")
    private String conclusions;

    @ApiModelProperty(value = "阳性结果")
    private String posistive;
}

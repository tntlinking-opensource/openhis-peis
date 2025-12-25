package com.center.medical.query.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 未检项目查询 分页返回数据
 */
@Data
public class FinishStatusVo implements Serializable {
    private static final long serialVersionUID = -7103965274932581426L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "使用套餐完成登记的时间")
    private Date dateregister;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "总检完成")
    private Double fFinalexamed;

    @ApiModelProperty(value = "jkwc")
    private Integer jkwc;

    @ApiModelProperty(value = "zywc")
    private Integer zywc;

    @ApiModelProperty(value = "jkyd")
    private String jkyd;

    @ApiModelProperty(value = "zyyd")
    private String zyyd;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String tjlx;

    @ApiModelProperty(value = "是否分检完成：0或null.否 1.是")
    private Integer fReadytofinal;

    @ApiModelProperty(value = "体检状态")
    private String tjzt;
}

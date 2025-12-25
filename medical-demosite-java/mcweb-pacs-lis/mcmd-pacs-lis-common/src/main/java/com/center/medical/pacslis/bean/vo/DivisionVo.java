package com.center.medical.pacslis.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 彩超审核查看分页
 */
@Data
public class DivisionVo implements Serializable {

    private static final long serialVersionUID = -1130468808700244489L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "体检类型")
    private String idExamtype;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "标识")
    private Integer flag;

    @ApiModelProperty(value = "是否从标软提取")
    private Integer fDiffperson;

    @ApiModelProperty(value = "收费检查项目名称")
    private String mc;

    @ApiModelProperty(value = "描述")
    private String ms;

    @ApiModelProperty(value = "检查结果总结")
    private String xj;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "操作人用户名")
    private String username;

}

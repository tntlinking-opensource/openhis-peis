package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团检加/弃项分页查询返回数据
 */
@Data
public class ItemListVo implements Serializable {

    private static final long serialVersionUID = 1132599562614738675L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "生日")
    private String birthdate;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "0:反禁检1:禁检")
    private String ispaused;

    @ApiModelProperty(value = "分组名")
    private String groupname;

    @ApiModelProperty(value = "分组id")
    private String groupid;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急")
    private String medicaltype;

    @ApiModelProperty(value = "订单号")
    private String ddh;
}

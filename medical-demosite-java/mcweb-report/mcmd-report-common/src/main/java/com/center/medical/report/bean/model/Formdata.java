package com.center.medical.report.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 保存更新数据
 */
@Data
public class Formdata implements Serializable {
    private static final long serialVersionUID = 1761710709550675545L;

    @ApiModelProperty(value = "接受人ID")
    private String revPersonId;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "交接时间")
    private String  revTime;



    @ApiModelProperty(value = "ids")
    private List<String> ids;

    @ApiModelProperty(value = "柜子号")
    private String chestNum;

    @ApiModelProperty(value = "发送方式ID")
    private String grantId;

    @ApiModelProperty(value = "类型：0.健康 1.职业(必填！！！)")
    private String diseaseHealth;


}

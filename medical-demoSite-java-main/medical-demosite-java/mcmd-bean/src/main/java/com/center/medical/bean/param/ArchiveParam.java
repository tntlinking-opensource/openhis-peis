package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-24 16:10
 * @description: 会员管理-档案合并查询参数
 */
public class ArchiveParam implements Serializable {

    private static final long serialVersionUID = -1735526952071677022L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String patientname;
}

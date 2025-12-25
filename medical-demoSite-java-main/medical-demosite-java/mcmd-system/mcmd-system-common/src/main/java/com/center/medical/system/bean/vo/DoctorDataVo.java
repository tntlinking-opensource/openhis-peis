package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取医生返回数据
 */
@Data
public class DoctorDataVo implements Serializable {
    private static final long serialVersionUID = -9172582267224405358L;


    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "classids")
    private String classids;

    @ApiModelProperty(value = "classnames")
    private String classnames;
}

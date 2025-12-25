package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 获取销售经理 返回数据
 */
@Data
public class FeeChargerDataVo implements Serializable {
    private static final long serialVersionUID = 6133238867373062452L;

    @ApiModelProperty(value = "用户编号")
    private String userNo;

    @ApiModelProperty(value = "用户名")
    private String username;


    public FeeChargerDataVo(String userNo, String username) {
        this.userNo = userNo;
        this.username = username;
    }

    public FeeChargerDataVo() {
    }
}

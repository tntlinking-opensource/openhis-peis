package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取家庭卡返回数据
 */
@Data
public class GetOldFamilyCardParam implements Serializable {
    private static final long serialVersionUID = 7487207950454480925L;


    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;


    public GetOldFamilyCardParam() {
    }


    public GetOldFamilyCardParam(String authCode, String idcardno) {
        this.authCode = authCode;
        this.idcardno = idcardno;
    }
}

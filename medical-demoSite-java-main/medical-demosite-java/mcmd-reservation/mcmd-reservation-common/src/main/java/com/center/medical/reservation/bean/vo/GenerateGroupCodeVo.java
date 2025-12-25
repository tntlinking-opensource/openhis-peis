package com.center.medical.reservation.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 生成分享码 返回数据
 */
@Data
public class GenerateGroupCodeVo implements Serializable {
    private static final long serialVersionUID = 5032445232733297142L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "提取码")
    private String extractedCode;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;


    @ApiModelProperty(value = "有效期,7,14,30,999")
    private Integer expirationDate;


    @ApiModelProperty(value = "过期时间")
    private Date expirationTime;


    @ApiModelProperty(value = "状态 0生效1过期")
    private Integer status;


    public GenerateGroupCodeVo() {
    }

    public GenerateGroupCodeVo(String id, String extractedCode, String idPatientclass, Integer expirationDate, Date expirationTime, Integer status) {
        this.id = id;
        this.extractedCode = extractedCode;
        this.idPatientclass = idPatientclass;
        this.expirationDate = expirationDate;
        this.expirationTime = expirationTime;
        this.status = status;
    }
}

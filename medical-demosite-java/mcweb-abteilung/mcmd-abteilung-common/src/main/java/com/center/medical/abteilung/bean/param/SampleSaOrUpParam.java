package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 检验样本-样本交接参数
 */
@Data
public class SampleSaOrUpParam implements Serializable {

    private static final long serialVersionUID = -5045047659821974326L;

    /**
     * griddata
     */
    @ApiModelProperty(value = "体检号")
    private List<String> patientCode;


    /**
     * formdata
     */
    @ApiModelProperty(value = "承接人")
    private String recipient;


    @ApiModelProperty(value = "交接时间yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

}

package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售员回款 分页 返回数据
 */
@Data
public class FIPageVo implements Serializable {
    private static final long serialVersionUID = 7638526753403162142L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户编号")
    private String userid;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;
    
    @ApiModelProperty(value = "1月")
    private String yy;

    @ApiModelProperty(value = "2月")
    private String ey;

    @ApiModelProperty(value = "3月")
    private String sy;

    @ApiModelProperty(value = "4月")
    private String iy;

    @ApiModelProperty(value = "5月")
    private String wy;

    @ApiModelProperty(value = "6月")
    private String ly;

    @ApiModelProperty(value = "7月")
    private String qy;

    @ApiModelProperty(value = "8月")
    private String ay;

    @ApiModelProperty(value = "9月")
    private String jy;

    @ApiModelProperty(value = "10月")
    private String oy;

    @ApiModelProperty(value = "11月")
    private String ny;

    @ApiModelProperty(value = "12月")
    private String dy;

    @ApiModelProperty(value = "备注")
    private String bz;

}

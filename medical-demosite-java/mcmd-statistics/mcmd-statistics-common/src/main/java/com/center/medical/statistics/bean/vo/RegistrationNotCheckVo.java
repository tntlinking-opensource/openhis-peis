package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记未检客户统计 分页返回数据
 */
@Data
public class RegistrationNotCheckVo implements Serializable {
    private static final long serialVersionUID = -8658396818796176476L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @Excel(name = "单位")
    @ApiModelProperty(value = "单位")
    private String khdwmc;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name="登记时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "登记时间")
    private String dateregister;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "收费方式")
    private String payway;

    @Excel(name = "收费项目名称")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "金额应付")
    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @Excel(name = "金额实付")
    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;



}

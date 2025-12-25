package com.center.medical.query.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目检况查询 分页参数
 */
@Data
public class StatusQueryParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -4399595488750825790L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = " 登记开始时间")
    private Date regStartTime;

    @ApiModelProperty(value = " 登记结束时间")
    private Date regEndTime;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "体检分类")
    private Integer useCodeHidden;

    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer examType;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "订单号")
    private String ddh;

}

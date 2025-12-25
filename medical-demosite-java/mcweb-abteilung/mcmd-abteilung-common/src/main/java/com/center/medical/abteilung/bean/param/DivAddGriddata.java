package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室加项保存保存表格数据
 */
@Data
public class DivAddGriddata implements Serializable {
    private static final long serialVersionUID = -1613472589898626038L;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "所属科室")
    private String ssks;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "收费项目ID")
    private String itemId;

    @ApiModelProperty(value = "加项医生")
    private String doctorUsername;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "uid")
    private String uid;

    @ApiModelProperty(value = "状态 removed:移除，modified:修改，added：新增")
    private String state;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "_id")
    private String _id;

    @ApiModelProperty(value = "备注")
    private String remarks;
}

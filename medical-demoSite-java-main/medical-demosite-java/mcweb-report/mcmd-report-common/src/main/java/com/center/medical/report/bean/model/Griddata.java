package com.center.medical.report.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存表格数据
 */
@Data
public class Griddata implements Serializable {
    private static final long serialVersionUID = -2858760643850682851L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "发送方式ID")
    private String grantId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "柜子号")
    private String  chestNum;

    @ApiModelProperty(value = "姓名")
    private String patientname;




}

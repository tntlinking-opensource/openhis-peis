package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 其他报告(MdOtherReport)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
@Data
@TableName("md_other_report")
@ApiModel(value = "MdOtherReport", description = "其他报告实体类")
public class MdOtherReport extends Model<MdOtherReport> implements Serializable {
    private static final long serialVersionUID = -45839196676374968L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "pdf地址")
    private String pdfUrl;

    @ApiModelProperty(value = "config_id")
    private String configId;

    @ApiModelProperty(value = "生成状态：null未生成 0.生成中 1.已生成")
    private Integer createStatus;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "生成时间")
    private String creater;

    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "报告类型，详见：com.world.center.bean.enums.ReportType")
    private Integer reportType;
}

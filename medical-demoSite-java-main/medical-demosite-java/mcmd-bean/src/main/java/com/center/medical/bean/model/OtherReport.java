package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 其他报告(OtherReport)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_other_report")
@ApiModel(value = "OtherReport", description = "其他报告实体类")
public class OtherReport extends Model<OtherReport> implements Serializable {
    private static final long serialVersionUID = 792901773346839444L;

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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告")
    private Integer reportType;


    public OtherReport() {
        super();
    }
    public OtherReport(String patientcode, String pdfUrl, String configId, Integer status, String errorMsg,
                       Date createTime, Integer type, String creater) {
        super();
        this.patientcode = patientcode;
        this.pdfUrl = pdfUrl;
        this.configId = configId;
        this.createStatus = status;
        this.errorMsg = errorMsg;
        this.createTime = createTime;
        this.reportType = type;
        this.creater = creater;
    }
}

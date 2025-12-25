package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 检验报告生成记录(InspectReport)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_inspect_report")
@ApiModel(value = "InspectReport", description = "检验报告生成记录实体类")
public class InspectReport extends Model<InspectReport> implements Serializable {
    private static final long serialVersionUID = 299362018384491936L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "最近打印时间")
    private Date printTime;

    @ApiModelProperty(value = "打印次数")
    private Integer printCount;
}

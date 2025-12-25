package com.center.medical.dicom.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接收失败的dicom文件(FailedDicomFile)表实体类
 *
 * @author makejava
 * @since 2023-09-12 14:34:44
 */
@Data
@TableName("md_failed_dicom_file")
@ApiModel(value = "FailedDicomFile", description = "接收失败的dicom文件实体类")
public class FailedDicomFile extends Model<FailedDicomFile> implements Serializable {
    private static final long serialVersionUID = -71405454791960510L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "接口类型")
    private String jklx;

    @ApiModelProperty(value = "部位")
    private String bodyPart;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "报错原因")
    private String remark;

    @ApiModelProperty(value = "redis的key")
    private String rkey;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否已处理  0否 1是")
    private Boolean isProcessed;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "开始时间")
    @TableField(exist = false)
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField(exist = false)
    private Date endTime;
}

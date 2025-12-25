package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * JC附件(Attachment)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:13
 */
@Data
@TableName("ATTACHMENT")
@ApiModel(value = "Attachment", description = "JC附件实体类")
public class Attachment extends Model<Attachment> implements Serializable {
    private static final long serialVersionUID = 563764562420846760L;

    @ApiModelProperty(value = "上传人")
    private String createId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "科室id")
    private String depId;

    @ApiModelProperty(value = "文件序列")
    private String fileSort;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "类型说明")
    private String fileType;

    @ApiModelProperty(value = "体检者收费项目ID（PACS）")
    private String feeItemId;

    @ApiModelProperty(value = "短号体检号")
    private String shortCode;

    @ApiModelProperty(value = "${column.comment}")
    private String pacsFeeItemId;

    @ApiModelProperty(value = "${column.comment}")
    private String configId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer inReport;

    @ApiModelProperty(value = "${column.comment}")
    private String bw;

    @ApiModelProperty(value = "${column.comment}")
    private String dcmPath;

    @ApiModelProperty(value = "${column.comment}")
    private String memo;
}

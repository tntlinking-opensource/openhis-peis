package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * JC附件(MdAttachment)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
@Data
@TableName("md_attachment")
@ApiModel(value = "MdAttachment", description = "JC附件实体类")
public class MdAttachment extends Model<MdAttachment> implements Serializable {
    private static final long serialVersionUID = 446587803853135813L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "文件序列")
    private String fileSort;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    @ApiModelProperty(value = "类型说明")
    private String fileType;

    @ApiModelProperty(value = "bw")
    private String bw;

    @ApiModelProperty(value = "dicom文件路径，高清屏使用")
    private String dcmPath;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "如果为空，取标识最小的attachmentConfig.保存文件时，用标识最大的CONFIG")
    private String configId;

    @ApiModelProperty(value = "是否进报告：0.否 1.是(PACS  彩超控制个检报告和科室报告)")
    private Integer inReport;

    @ApiModelProperty(value = "上传者")
    private String createId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;

    @ApiModelProperty(value = "体检者收费项目ID（PACS）")
    private String feeItemId;

    @ApiModelProperty(value = "PACS系统的体检者收费项目ID")
    private String pacsFeeItemId;

    @ApiModelProperty(value = "科室id")
    private String depId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "文件类型：1.图片 2.视频 3.文件")
    private Integer type;

    @ApiModelProperty(value = "分组ID")
    private String fileGroupId;

    @ApiModelProperty(value = "文件状态：0.正常 1.待删除文件 2.已删除文件待同步（线上线下同步）")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

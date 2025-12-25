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
 * 上传文件记录表(MdAttachFile)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
@Data
@TableName("md_attach_file")
@ApiModel(value = "MdAttachFile", description = "上传文件记录表实体类")
public class MdAttachFile extends Model<MdAttachFile> implements Serializable {
    private static final long serialVersionUID = -52926620466075775L;

    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "文件ID")
    private String fileId;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "文件类型：1.图片 2.视频 3.文件")
    private Integer type;

    @ApiModelProperty(value = "文件分组id")
    private String attachFileGroupId;

    @ApiModelProperty(value = "文件状态：0.正常 1.待删除文件 2.已删除文件待同步（线上线下同步）")
    private String status;
}

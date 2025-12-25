package com.center.medical.app.bean.model;

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
 * 上传文件记录表(AttachFile)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:05
 */
@Data
@TableName("md_attach_file")
@ApiModel(value = "AttachFile", description = "上传文件记录表实体类")
public class AttachFile extends Model<AttachFile> implements Serializable {
    private static final long serialVersionUID = 344818631404473079L;

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
}

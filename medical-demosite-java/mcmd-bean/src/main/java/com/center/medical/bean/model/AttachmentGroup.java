package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件分组表(AttachmentGroup)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:05
 */
@Data
@TableName("md_attachment_group")
@ApiModel(value = "AttachmentGroup", description = "文件分组表实体类")
public class AttachmentGroup extends Model<AttachmentGroup> implements Serializable {
    private static final long serialVersionUID = -14188513930428062L;

    @TableId(value = "file_group_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "文件分组ID")
    private String fileGroupId;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "分组名称")
    private String name;

    @ApiModelProperty(value = "文件类型：1.图片 2.视频 3.文件")
    private Integer type;
}

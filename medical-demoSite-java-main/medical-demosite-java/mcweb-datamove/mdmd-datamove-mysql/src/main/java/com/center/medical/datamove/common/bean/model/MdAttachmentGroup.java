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
 * 文件分组表(MdAttachmentGroup)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:07
 */
@Data
@TableName("md_attachment_group")
@ApiModel(value = "MdAttachmentGroup", description = "文件分组表实体类")
public class MdAttachmentGroup extends Model<MdAttachmentGroup> implements Serializable {
    private static final long serialVersionUID = 818677179601749502L;

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

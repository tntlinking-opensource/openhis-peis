package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录有文件改变的文件目录(FileChangeRecord)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_file_change_record")
@ApiModel(value = "FileChangeRecord", description = "记录有文件改变的文件目录实体类")
public class FileChangeRecord extends Model<FileChangeRecord> implements Serializable {
    private static final long serialVersionUID = -18488208815355607L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步")
    private Integer status;

    @ApiModelProperty(value = "目录")
    private String dir;
}

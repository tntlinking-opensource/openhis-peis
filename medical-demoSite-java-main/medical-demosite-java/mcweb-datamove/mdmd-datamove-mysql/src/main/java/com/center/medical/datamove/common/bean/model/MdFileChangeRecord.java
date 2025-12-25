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
 * 记录有文件改变的文件目录(MdFileChangeRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
@Data
@TableName("md_file_change_record")
@ApiModel(value = "MdFileChangeRecord", description = "记录有文件改变的文件目录实体类")
public class MdFileChangeRecord extends Model<MdFileChangeRecord> implements Serializable {
    private static final long serialVersionUID = -64066241238758292L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "同步状态：0.未同步 1.已同步")
    private Integer status;

    @ApiModelProperty(value = "目录")
    private String dir;
}

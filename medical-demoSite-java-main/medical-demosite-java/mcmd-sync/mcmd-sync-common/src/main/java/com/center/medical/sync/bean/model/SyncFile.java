package com.center.medical.sync.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 同步文件操作(SyncFile)表实体类
 *
 * @author makejava
 * @since 2023-09-12 10:25:45
 */
@Data
@TableName("md_sync_file")
@ApiModel(value = "SyncFile", description = "同步文件操作实体类")
public class SyncFile extends Model<SyncFile> implements Serializable {
    private static final long serialVersionUID = -67892238301155038L;

    @TableId(value = "id")
    @ApiModelProperty(value = "操作ID")
    private Long id;

    @ApiModelProperty(value = "是否全部中心同步：0.否 1.是")
    private Integer isAll;

    @ApiModelProperty(value = "同步到指定中心的ID集合")
    private String branchIds;

    @ApiModelProperty(value = "同步状态：0.待同步 1.同步中 2.已完成同步 3.同步失败待重新同步 4.失效")
    private Integer status;

    @ApiModelProperty(value = "图片路径，多张以,隔开")
    private String imageUrl;

    @ApiModelProperty(value = "操作类型：1.新增 2.删除")
    private Integer optionType;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}

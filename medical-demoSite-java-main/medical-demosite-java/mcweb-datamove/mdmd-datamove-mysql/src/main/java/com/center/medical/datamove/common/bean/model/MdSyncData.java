package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 同步数据操作(MdSyncData)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:49
 */
@Data
@TableName("md_sync_data")
@ApiModel(value = "MdSyncData", description = "同步数据操作实体类")
public class MdSyncData extends Model<MdSyncData> implements Serializable {
    private static final long serialVersionUID = 328266820957089231L;

    @TableId(value = "id")
    @ApiModelProperty(value = "操作ID")
    private Long id;

    @ApiModelProperty(value = "数据类型，见表：md_sync_data_type")
    private Integer dataType;

    @ApiModelProperty(value = "操作类型：2.新增 3.更新 4.删除")
    private Integer optType;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "同步的数据库")
    private String bizDb;

    @ApiModelProperty(value = "同步的数据表")
    private String bizTable;

    @ApiModelProperty(value = "关联的数据ID集合")
    private String bizId;

    @ApiModelProperty(value = "操作对象的更新时间")
    private Date bizModifydate;

    @ApiModelProperty(value = "是否全部中心同步：0.否 1.是")
    private Integer isAll;

    @ApiModelProperty(value = "同步到指定中心的ID集合")
    private String branchIds;

    @ApiModelProperty(value = "同步状态：0.待同步 1.同步中 2.已完成同步 3.同步失败待重新同步 4.失效")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}

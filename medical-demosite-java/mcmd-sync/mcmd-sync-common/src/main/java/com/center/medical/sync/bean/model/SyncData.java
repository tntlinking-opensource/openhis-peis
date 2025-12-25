package com.center.medical.sync.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 同步数据操作(SyncData)表实体类
 *
 * @author makejava
 * @since 2023-07-17 15:35:17
 */
@Data
@TableName("md_sync_data")
@ApiModel(value = "SyncData", description = "同步数据操作实体类")
public class SyncData extends Model<SyncData> implements Serializable {
    private static final long serialVersionUID = -19736209758053756L;

    @TableId(value = "id")
    @Excel(name = "id")
    @ApiModelProperty(value = "操作ID")
    private Long id;

    @Excel(name = "dataType")
    @ApiModelProperty(value = "数据类型，见表：md_sync_data_type")
    private Integer dataType;

    @Excel(name = "optType")
    @ApiModelProperty(value = "操作类型：2.新增 3.更新 4.删除")
    private Integer optType;

    @Excel(name = "version")
    @ApiModelProperty(value = "版本号")
    private Integer version;

    @Excel(name = "bizDb")
    @ApiModelProperty(value = "同步的数据库")
    private String bizDb;

    @Excel(name = "bizTable")
    @ApiModelProperty(value = "同步的数据表")
    private String bizTable;

    @Excel(name = "bizId")
    @ApiModelProperty(value = "关联的数据ID集合")
    private String bizId;

    @Excel(name = "bizModifydate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作对象的更新时间")
    private Date bizModifydate;

    @Excel(name = "isAll")
    @ApiModelProperty(value = "是否全部中心同步：0.否 1.是")
    private Integer isAll;

    @Excel(name = "branchIds")
    @ApiModelProperty(value = "同步到指定中心的ID集合")
    private String branchIds;

    @Excel(name = "status")
    @ApiModelProperty(value = "同步状态：0.待同步 1.同步中 2.已完成同步 3.同步失败待重新同步 4.失效")
    private Integer status;

    @Excel(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Excel(name = "createdate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @Excel(name = "modifydate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}

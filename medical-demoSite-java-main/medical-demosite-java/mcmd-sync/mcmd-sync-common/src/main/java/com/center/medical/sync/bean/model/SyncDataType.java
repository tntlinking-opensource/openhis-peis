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
 * 同步数据类型(SyncDataType)表实体类
 *
 * @author makejava
 * @since 2023-07-17 16:01:34
 */
@Data
@TableName("md_sync_data_type")
@ApiModel(value = "SyncDataType", description = "同步数据类型实体类")
public class SyncDataType extends Model<SyncDataType> implements Serializable {
    private static final long serialVersionUID = -14539377422806175L;

    @TableId(value = "type_id")
    @ApiModelProperty(value = "类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "事务编号")
    private Integer orderNo;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;
}

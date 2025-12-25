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
 * 同步数据类型(MdSyncDataType)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:52
 */
@Data
@TableName("md_sync_data_type")
@ApiModel(value = "MdSyncDataType", description = "同步数据类型实体类")
public class MdSyncDataType extends Model<MdSyncDataType> implements Serializable {
    private static final long serialVersionUID = 580628858570318831L;

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
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}

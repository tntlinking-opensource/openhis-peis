package com.center.medical.datamove.oracle.bean.model;


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
 * 批量发送短信记录(BatchSmsRecord)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:47
 */
@Data
@TableName("BATCH_SMS_RECORD")
@ApiModel(value = "BatchSmsRecord", description = "批量发送短信记录实体类")
public class BatchSmsRecord extends Model<BatchSmsRecord> implements Serializable {
    private static final long serialVersionUID = 979450979253144709L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;
}

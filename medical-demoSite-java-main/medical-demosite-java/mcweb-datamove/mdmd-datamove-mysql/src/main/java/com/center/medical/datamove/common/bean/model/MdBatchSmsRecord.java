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
 * 批量发送短信记录(MdBatchSmsRecord)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
@Data
@TableName("md_batch_sms_record")
@ApiModel(value = "MdBatchSmsRecord", description = "批量发送短信记录实体类")
public class MdBatchSmsRecord extends Model<MdBatchSmsRecord> implements Serializable {
    private static final long serialVersionUID = -46489648447795733L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "状态")
    private Integer status;
}

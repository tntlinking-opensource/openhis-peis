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
 * 当前排队绑定信息(MdCurrentQueueInfo)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Data
@TableName("md_current_queue_info")
@ApiModel(value = "MdCurrentQueueInfo", description = "当前排队绑定信息实体类")
public class MdCurrentQueueInfo extends Model<MdCurrentQueueInfo> implements Serializable {
    private static final long serialVersionUID = -70252749831622526L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "绑定时间")
    private Date createDate;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "体检号")
    private String patientCode;
}

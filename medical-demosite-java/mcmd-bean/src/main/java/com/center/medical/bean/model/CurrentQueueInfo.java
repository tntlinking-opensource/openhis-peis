package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当前排队绑定信息(CurrentQueueInfo)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:57
 */
@Data
@TableName("md_current_queue_info")
@ApiModel(value = "CurrentQueueInfo", description = "当前排队绑定信息实体类")
public class CurrentQueueInfo extends Model<CurrentQueueInfo> implements Serializable {
    private static final long serialVersionUID = 288048531187202642L;

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

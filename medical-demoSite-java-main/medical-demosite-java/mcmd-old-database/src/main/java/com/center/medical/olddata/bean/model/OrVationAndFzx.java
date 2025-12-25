package com.center.medical.olddata.bean.model;


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
 * 团体任务分中心（不会被同步）(MdVationAndFzx)表实体类
 *
 * @author ay
 * @since 2024-04-11 11:00:25
 */
@Data
@TableName("vation_and_fzx")
@ApiModel(value = "OrVationAndFzx", description = "团体任务分中心（不会被同步）实体类")
public class OrVationAndFzx extends Model<OrVationAndFzx> implements Serializable {
    private static final long serialVersionUID = 795393258635975736L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "团体任务ID")
    private String vationId;


    @ApiModelProperty(value = "分中心ID")
    private String fzxId;


    @ApiModelProperty(value = "下载状态：0未下载 1已下载")
    private Integer xzzt;

}

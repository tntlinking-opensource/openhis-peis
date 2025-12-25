package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2024-04-11 10:54:31
 */
@Data
@TableName("md_vation_and_fzx")
@ApiModel(value = "MdVationAndFzx", description = "团体任务分中心（不会被同步）实体类")
public class MdVationAndFzx extends Model<MdVationAndFzx> implements Serializable {
    private static final long serialVersionUID = 562940898621763990L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "团体任务ID")
    private String vationId;


    @ApiModelProperty(value = "分中心ID")
    private String fzxId;


    @ApiModelProperty(value = "下载状态：0未下载 1已下载")
    private Integer xzzt;


    public MdVationAndFzx(String vationId, String fzxId) {
        this.vationId = vationId;
        this.fzxId = fzxId;
    }


    public MdVationAndFzx() {
    }
}

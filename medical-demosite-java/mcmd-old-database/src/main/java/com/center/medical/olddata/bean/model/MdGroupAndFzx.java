package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分组分中心(MdGroupAndFzx)表实体类
 *
 * @author ay
 * @since 2024-04-11 10:49:53
 */
@Data
@TableName("md_group_and_fzx")
@ApiModel(value = "MdGroupAndFzx", description = "分组分中心实体类")
public class MdGroupAndFzx extends Model<MdGroupAndFzx> implements Serializable {
    private static final long serialVersionUID = 826983819975387795L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "分组ID")
    private String groupId;


    @ApiModelProperty(value = "分中心ID")
    private String fzxId;


    @ApiModelProperty(value = "下载状态：0.未下载 1.已下载")
    private Integer xzzt;


    public MdGroupAndFzx(String groupId, String fzxId) {
        this.groupId = groupId;
        this.fzxId = fzxId;
    }


    public MdGroupAndFzx() {
    }
}

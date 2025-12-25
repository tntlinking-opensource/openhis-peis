package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分组分中心(GroupAndFzx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:00
 */
@Data
@TableName("md_group_and_fzx")
@ApiModel(value = "GroupAndFzx", description = "分组分中心实体类")
public class GroupAndFzx extends Model<GroupAndFzx> implements Serializable {
    private static final long serialVersionUID = -57516382621151889L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "分组ID")
    private String groupId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "下载状态：0.未下载 1.已下载")
    private Integer xzzt;


    public GroupAndFzx() {
        super();
    }

    public GroupAndFzx(String fzxId, String groupId, Integer xzzt) {
        super();
        this.fzxId = fzxId;
        this.groupId = groupId;
        this.xzzt = xzzt;
    }
}

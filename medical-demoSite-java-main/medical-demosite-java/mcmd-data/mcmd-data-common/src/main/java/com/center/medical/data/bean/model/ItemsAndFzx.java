package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:06
 */
@Data
@TableName("md_items_and_fzx")
@ApiModel(value = "ItemsAndFzx", description = "JC收费项目和分中心关联表实体类")
public class ItemsAndFzx extends Model<ItemsAndFzx> implements Serializable {
    private static final long serialVersionUID = 659926874391103841L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目ID")
    private String itemsId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态：0未同步 1已同步")
    private Integer tbzt;
}

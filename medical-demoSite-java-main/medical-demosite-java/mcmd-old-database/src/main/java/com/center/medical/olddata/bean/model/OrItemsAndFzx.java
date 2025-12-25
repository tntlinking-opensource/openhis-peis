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
 * JC收费项目和分中心关联表(ItemsAndFzx)表实体类
 *
 * @author ay
 * @since 2024-07-13 14:27:28
 */
@Data
@TableName("ITEMS_AND_FZX")
@ApiModel(value = "ItemsAndFzx", description = "JC收费项目和分中心关联表实体类")
public class OrItemsAndFzx extends Model<OrItemsAndFzx> implements Serializable {
    private static final long serialVersionUID = 170906381739042788L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "修改时间")
    private Date modifydate;


    @ApiModelProperty(value = "收费项目ID")
    private String itemsId;


    @ApiModelProperty(value = "分中心ID")
    private String fzxId;


    @ApiModelProperty(value = "同步状态")
    private Integer tbzt;

}

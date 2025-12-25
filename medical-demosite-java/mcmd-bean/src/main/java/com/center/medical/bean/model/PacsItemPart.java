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
 * 项目部位表(PacsItemPart)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_item_part")
@ApiModel(value = "PacsItemPart", description = "项目部位表实体类")
public class PacsItemPart extends Model<PacsItemPart> implements Serializable {
    private static final long serialVersionUID = -99234610115983734L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "pacs收费项目id")
    private String itemId;

    @ApiModelProperty(value = "pacs部位id")
    private String partId;
}

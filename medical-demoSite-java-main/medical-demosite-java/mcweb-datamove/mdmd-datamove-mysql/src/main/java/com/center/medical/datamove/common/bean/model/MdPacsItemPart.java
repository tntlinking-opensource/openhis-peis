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
 * 项目部位表(MdPacsItemPart)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
@Data
@TableName("md_pacs_item_part")
@ApiModel(value = "MdPacsItemPart", description = "项目部位表实体类")
public class MdPacsItemPart extends Model<MdPacsItemPart> implements Serializable {
    private static final long serialVersionUID = 738928893882750954L;

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

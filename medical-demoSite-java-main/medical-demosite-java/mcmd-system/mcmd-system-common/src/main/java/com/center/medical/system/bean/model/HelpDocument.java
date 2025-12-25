package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 帮助文档(HelpDocument)表实体类
 *
 * @author ay
 * @since 2024-04-24 13:56:58
 */
@Data
@TableName("md_help_document")
@ApiModel(value = "HelpDocument", description = "帮助文档实体类")
public class HelpDocument extends Model<HelpDocument> implements Serializable {
    private static final long serialVersionUID = 132707196137417432L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "类型标识")
    private String type;


    @ApiModelProperty(value = "文档名称")
    private String documentname;


    @ApiModelProperty(value = "pdf地址")
    private String urlPdf;


    @ApiModelProperty(value = "是否已删除：0.否 1.是")
    private Integer isDelete;


    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

}

package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序文章类型(AppArticleType)表实体类
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
@Data
@TableName("md_app_article_type")
@ApiModel(value = "AppArticleType", description = "小程序文章类型实体类")
public class AppArticleType extends Model<AppArticleType> implements Serializable {
    private static final long serialVersionUID = 575589640023485544L;

    @TableId(value = "id")
    @ApiModelProperty(value = "主键")
    private String id;


    @ApiModelProperty(value = "名称")
    private String name;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更改日期")
    private Date modifydate;


    @ApiModelProperty(value = "备注")
    private String note;


    @ApiModelProperty(value = "序号")
    private Integer seq;

}

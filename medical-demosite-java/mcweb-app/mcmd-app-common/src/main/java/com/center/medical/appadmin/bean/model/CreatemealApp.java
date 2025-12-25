package com.center.medical.appadmin.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序套餐表(CreatemealApp)表实体类
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
@Data
@TableName("md_createmeal_app")
@ApiModel(value = "CreatemealApp", description = "小程序套餐表实体类")
public class CreatemealApp extends Model<CreatemealApp> implements Serializable {
    private static final long serialVersionUID = 832262658617861827L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "套餐ID")
    private String tcid;


    @ApiModelProperty(value = "类型")
    private Integer type;


    @ApiModelProperty(value = "缩略图")
    private String thumbnail;


    @ApiModelProperty(value = "标签名,用英文逗号隔开")
    private String label;


    @ApiModelProperty(value = "图片")
    private String imgUrl;


    @ApiModelProperty(value = "内容")
    private String content;


    @ApiModelProperty(value = "备注")
    private String bz;


    @ApiModelProperty(value = "状态 -1下线, 0待处理 ,1上线 ")
    private Integer status;


    @ApiModelProperty(value = "顺序")
    private Integer seq;


    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @TableField(exist = false)
    @ApiModelProperty(value = "分类名称")
    private String typeName;


    @ApiModelProperty(value = "小程序套餐名称")
    private String appTcmc;
}

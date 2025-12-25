package com.center.medical.app.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序套餐类型(CreatemealAppType)表实体类
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
@Data
@TableName("md_createmeal_app_type")
@ApiModel(value = "CreatemealAppType", description = "小程序套餐类型实体类")
public class CreatemealAppType extends Model<CreatemealAppType> implements Serializable {
    private static final long serialVersionUID = -68578126547609202L;

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

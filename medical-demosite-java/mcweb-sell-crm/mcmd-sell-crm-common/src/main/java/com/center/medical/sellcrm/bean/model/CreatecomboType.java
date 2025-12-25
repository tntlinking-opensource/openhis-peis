package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 最小套餐分类(CreatecomboType)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-14 18:48:08
 */
@Data
@TableName("md_createcombo_type")
@ApiModel(value = "CreatecomboType", description = "最小套餐分类实体类")
public class CreatecomboType extends Model<CreatecomboType> implements Serializable {
    private static final long serialVersionUID = 223046625406299214L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "序号")
    private Integer typeIndex;

    @ApiModelProperty(value = "状态")
    private Integer typeState;
}

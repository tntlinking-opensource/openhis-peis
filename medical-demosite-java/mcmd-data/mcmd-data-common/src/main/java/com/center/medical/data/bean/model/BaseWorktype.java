package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工种(BaseWorktype)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_base_worktype")
@ApiModel(value = "BaseWorktype", description = "工种实体类")
public class BaseWorktype extends Model<BaseWorktype> implements Serializable {
    private static final long serialVersionUID = 637335415918315375L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "工种名称")
    private String typeName;

    @ApiModelProperty(value = "青岛代码")
    private String qingdaoCode;

    @ApiModelProperty(value = "济南代码")
    private String jinanCode;

    @ApiModelProperty(value = "长沙代码")
    private String changshaCode;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

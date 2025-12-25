package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 济南-计量单位(BaseUnit)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_base_unit")
@ApiModel(value = "BaseUnit", description = "济南-计量单位实体类")
public class BaseUnit extends Model<BaseUnit> implements Serializable {
    private static final long serialVersionUID = 696258460641206742L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "检查项目代码（济南）")
    private String examCode;

    @ApiModelProperty(value = "计量单位编码")
    private String codeNo;

    @ApiModelProperty(value = "计量单位名称")
    private String codeName;
}

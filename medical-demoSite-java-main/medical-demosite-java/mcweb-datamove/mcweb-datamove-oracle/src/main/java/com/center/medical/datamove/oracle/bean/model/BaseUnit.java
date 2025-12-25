package com.center.medical.datamove.oracle.bean.model;


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
 * 济南-计量单位(BaseUnit)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:37
 */
@Data
@TableName("BASE_UNIT")
@ApiModel(value = "BaseUnit", description = "济南-计量单位实体类")
public class BaseUnit extends Model<BaseUnit> implements Serializable {
    private static final long serialVersionUID = -48319196496911747L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "检查项目代码（济南）")
    private String examCode;

    @ApiModelProperty(value = "计量单位编码")
    private String codeNo;

    @ApiModelProperty(value = "计量单位名称")
    private String codeName;
}

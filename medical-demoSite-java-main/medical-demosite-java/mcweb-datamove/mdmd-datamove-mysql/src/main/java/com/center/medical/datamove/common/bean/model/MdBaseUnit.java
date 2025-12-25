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
 * 济南-计量单位(MdBaseUnit)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Data
@TableName("md_base_unit")
@ApiModel(value = "MdBaseUnit", description = "济南-计量单位实体类")
public class MdBaseUnit extends Model<MdBaseUnit> implements Serializable {
    private static final long serialVersionUID = -63036070353826551L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "检查项目代码（济南）")
    private String examCode;

    @ApiModelProperty(value = "计量单位编码")
    private String codeNo;

    @ApiModelProperty(value = "计量单位名称")
    private String codeName;
}

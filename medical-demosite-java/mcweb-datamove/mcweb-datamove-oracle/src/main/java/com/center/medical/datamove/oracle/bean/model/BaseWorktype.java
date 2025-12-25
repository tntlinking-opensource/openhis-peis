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
 * 工种(BaseWorktype)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:38
 */
@Data
@TableName("BASE_WORKTYPE")
@ApiModel(value = "BaseWorktype", description = "工种实体类")
public class BaseWorktype extends Model<BaseWorktype> implements Serializable {
    private static final long serialVersionUID = 647068849614465177L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "工种名称")
    private String typeName;

    @ApiModelProperty(value = "青岛代码")
    private String qingdaoCode;

    @ApiModelProperty(value = "济南代码")
    private String jinanCode;

    @ApiModelProperty(value = "长沙代码")
    private String changshaCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;
}

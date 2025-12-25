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
 * 样本类型(MdYblx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
@Data
@TableName("md_yblx")
@ApiModel(value = "MdYblx", description = "样本类型实体类")
public class MdYblx extends Model<MdYblx> implements Serializable {
    private static final long serialVersionUID = -99652781036455278L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "样本名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "序号")
    private Integer xh;
}

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
 * 企业经济类型(MdQyjjlx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Data
@TableName("md_qyjjlx")
@ApiModel(value = "MdQyjjlx", description = "企业经济类型实体类")
public class MdQyjjlx extends Model<MdQyjjlx> implements Serializable {
    private static final long serialVersionUID = -53381745846822594L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "企业经济类型名称")
    private String qyjjlx;
}

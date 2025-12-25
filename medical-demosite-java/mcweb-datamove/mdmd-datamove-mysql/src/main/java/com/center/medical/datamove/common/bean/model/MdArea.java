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
 * 籍贯表(MdArea)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
@Data
@TableName("md_area")
@ApiModel(value = "MdArea", description = "籍贯表实体类")
public class MdArea extends Model<MdArea> implements Serializable {
    private static final long serialVersionUID = -54354871696029391L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "省份名称")
    private String resarea;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否默认")
    private String isDefault;
}

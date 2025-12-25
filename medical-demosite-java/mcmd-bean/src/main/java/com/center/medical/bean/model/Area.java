package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 籍贯表(Area)表实体类
 *
 * @author ay
 * @since 2022-11-18 14:46:10
 */
@Data
@TableName("md_area")
@ApiModel(value = "Area", description = "籍贯表实体类")
public class Area extends Model<Area> implements Serializable {
    private static final long serialVersionUID = -29861292141028885L;

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
    private Integer isDefault;
}

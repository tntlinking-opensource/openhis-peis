package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务类型(MdBusinessCat)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Data
@TableName("md_business_cat")
@ApiModel(value = "MdBusinessCat", description = "业务类型实体类")
public class MdBusinessCat extends Model<MdBusinessCat> implements Serializable {
    private static final long serialVersionUID = -35765385015669053L;

    @TableId(value = "type_id")
    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "父节点")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String typeName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "介绍")
    private String tip;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "状态：-1.删除 0.下线 1.正常")
    private Integer status;

    @ApiModelProperty(value = "层级")
    private Integer grade;

    @ApiModelProperty(value = "记录时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}

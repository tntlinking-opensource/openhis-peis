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
 * 维护最小套餐与收费项目关联表(MdComboanditem)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
@Data
@TableName("md_comboanditem")
@ApiModel(value = "MdComboanditem", description = "维护最小套餐与收费项目关联表实体类")
public class MdComboanditem extends Model<MdComboanditem> implements Serializable {
    private static final long serialVersionUID = 309517534724943887L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "收费项目ID")
    private String sfxmid;

    @ApiModelProperty(value = "是否备选")
    private String sfbx;

    @ApiModelProperty(value = "是否可以删除：0或null.不可以 1.可以")
    private Integer isDelete;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否必检：0.选检 1.必检 ")
    private Integer sfbj;

    @ApiModelProperty(value = "item_sort")
    private Integer itemSort;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}

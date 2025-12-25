package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 维护最小套餐与收费项目关联表(Comboanditem)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("md_comboanditem")
@ApiModel(value = "Comboanditem", description = "维护最小套餐与收费项目关联表实体类")
public class Comboanditem extends Model<Comboanditem> implements Serializable {
    private static final long serialVersionUID = 957309382950699176L;

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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否必检：0.选检 1.必检 ")
    private Integer sfbj;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "item_sort")
    private Integer itemSort;

}

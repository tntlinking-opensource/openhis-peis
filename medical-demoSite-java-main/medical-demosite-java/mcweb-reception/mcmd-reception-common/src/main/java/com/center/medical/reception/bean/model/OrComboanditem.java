package com.center.medical.reception.bean.model;


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
 * 维护最小套餐与收费项目关联表(Comboanditem)表实体类
 *
 * @author ay
 * @since 2023-07-25 22:07:21
 */
@Data
@TableName("COMBOANDITEM")
@ApiModel(value = "Comboanditem", description = "维护最小套餐与收费项目关联表实体类")
public class OrComboanditem extends Model<OrComboanditem> implements Serializable {
    private static final long serialVersionUID = 448243749138171010L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "收费项目ID")
    private String sfxmid;

    @ApiModelProperty(value = "是否备选")
    private String sfbx;

    @ApiModelProperty(value = "0：不能删  1：能删-----主要标记在订单里面的最小收费项目是不能删除的")
    private Integer isDelete;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否必检 1必检 0选检")
    private Integer sfbj;

    @ApiModelProperty(value = "${column.comment}")
    private Integer itemSort;
}

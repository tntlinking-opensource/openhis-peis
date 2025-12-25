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
 * 订单柜子信息(MdChest)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:16
 */
@Data
@TableName("md_chest")
@ApiModel(value = "MdChest", description = "订单柜子信息实体类")
public class MdChest extends Model<MdChest> implements Serializable {
    private static final long serialVersionUID = 475296113310178951L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "柜子号")
    private String gzh;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "编号")
    private String chestNum;
}

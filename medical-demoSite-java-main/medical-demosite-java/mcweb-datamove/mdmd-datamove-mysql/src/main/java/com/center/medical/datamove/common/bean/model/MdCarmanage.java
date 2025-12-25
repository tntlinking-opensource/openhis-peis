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
 * 体检车管理(MdCarmanage)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
@Data
@TableName("md_carmanage")
@ApiModel(value = "MdCarmanage", description = "体检车管理实体类")
public class MdCarmanage extends Model<MdCarmanage> implements Serializable {
    private static final long serialVersionUID = -66734597066896329L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "车牌号")
    private String carNo;

    @ApiModelProperty(value = "司机")
    private String idDriver;

    @ApiModelProperty(value = "订单ID")
    private String idOrder;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "体检地址")
    private String address;

    @ApiModelProperty(value = "外出开始时间")
    private Date outTimeStart;

    @ApiModelProperty(value = "外出结束时间")
    private Date outTimeEnd;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createer;
}

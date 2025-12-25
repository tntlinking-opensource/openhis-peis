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
 * 排检(MdPeissortexam)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
@Data
@TableName("md_peissortexam")
@ApiModel(value = "MdPeissortexam", description = "排检实体类")
public class MdPeissortexam extends Model<MdPeissortexam> implements Serializable {
    private static final long serialVersionUID = 409611125349343054L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "团体分组id")
    private String groupId;

    @ApiModelProperty(value = "天")
    private Date sortDate;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "序号")
    private Integer sortNum;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "上午人数")
    private Integer am;

    @ApiModelProperty(value = "下午人数")
    private Integer pm;

    @ApiModelProperty(value = "预约人用户名")
    private String creator;

    @ApiModelProperty(value = "备注")
    private String bz;
}

package com.center.medical.datamove.oracle.bean.model;


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
 * KS外送登记结果主表(OutsideMain)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:34
 */
@Data
@TableName("OUTSIDE_MAIN")
@ApiModel(value = "OutsideMain", description = "KS外送登记结果主表实体类")
public class OutsideMain extends Model<OutsideMain> implements Serializable {
    private static final long serialVersionUID = -49946399946059207L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "外送人")
    private String sendPeople;

    @ApiModelProperty(value = "外送时间")
    private Date sendDate;

    @ApiModelProperty(value = "返回人")
    private String backPeople;

    @ApiModelProperty(value = "返回时间")
    private Date backDate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;
}

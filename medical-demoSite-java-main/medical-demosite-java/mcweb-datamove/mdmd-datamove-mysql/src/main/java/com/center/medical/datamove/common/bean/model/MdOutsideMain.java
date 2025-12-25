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
 * KS外送登记结果主表(MdOutsideMain)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
@Data
@TableName("md_outside_main")
@ApiModel(value = "MdOutsideMain", description = "KS外送登记结果主表实体类")
public class MdOutsideMain extends Model<MdOutsideMain> implements Serializable {
    private static final long serialVersionUID = -61244514875658894L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "处理状态")
    private Integer status;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

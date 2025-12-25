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
 * 预约管理(Reservation)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:19
 */
@Data
@TableName("RESERVATION")
@ApiModel(value = "Reservation", description = "预约管理实体类")
public class Reservation extends Model<Reservation> implements Serializable {
    private static final long serialVersionUID = 660816206219516506L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "预约员ID")
    private String idReservation;

    @ApiModelProperty(value = "审批人ID")
    private String idCheck;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "0:预留申请 1：超额申请")
    private Integer type;

    @ApiModelProperty(value = "体检者类型ID")
    private String idPatientclass;

    @ApiModelProperty(value = "0：提交申请 1：审核通过 2：审核不通过")
    private Integer approvedStatus;

    @ApiModelProperty(value = "0 未删除 1 删除")
    private Integer isDelete;

    @ApiModelProperty(value = "体检时间")
    private Date medicaldate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String successcode;

    @ApiModelProperty(value = "${column.comment}")
    private String idSex;

    @ApiModelProperty(value = "${column.comment}")
    private String idExamtype;
}

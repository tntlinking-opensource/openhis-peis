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
 * 记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:02
 */
@Data
@TableName("CUSTOMER_OPERATE_HISTORY")
@ApiModel(value = "CustomerOperateHistory", description = "记录客户释放、领取、流失、分配、领导释放、线程释放实体类")
public class CustomerOperateHistory extends Model<CustomerOperateHistory> implements Serializable {
    private static final long serialVersionUID = -37702496251803404L;

    @ApiModelProperty(value = "${column.comment}")
    private String operateId;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String khdwid;

    @ApiModelProperty(value = "${column.comment}")
    private String khdwmc;

    @ApiModelProperty(value = "${column.comment}")
    private String operateType;

    @ApiModelProperty(value = "${column.comment}")
    private String xsjlid;

    @ApiModelProperty(value = "${column.comment}")
    private String xsjlmc;

    @ApiModelProperty(value = "${column.comment}")
    private String operateName;

    @ApiModelProperty(value = "${column.comment}")
    private String fprid;

    @ApiModelProperty(value = "${column.comment}")
    private String fprmc;

    @ApiModelProperty(value = "${column.comment}")
    private String fromXsjlid;

    @ApiModelProperty(value = "${column.comment}")
    private String fromXsjlname;

    @ApiModelProperty(value = "${column.comment}")
    private Integer transType;
}

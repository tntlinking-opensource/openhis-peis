package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (Kingdeeremittance)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:19:29
 */
@Data
@TableName("KINGDEEREMITTANCE")
@ApiModel(value = "Kingdeeremittance", description = "$tableInfo.comment实体类")
public class Kingdeeremittance extends Model<Kingdeeremittance> implements Serializable {
    private static final long serialVersionUID = 953360136174182288L;

    @ApiModelProperty(value = "${column.comment}")
    private String transactionnumber;

    @ApiModelProperty(value = "${column.comment}")
    private String remitter;

    @ApiModelProperty(value = "${column.comment}")
    private String income;

    @ApiModelProperty(value = "${column.comment}")
    private Date transdate;

    @ApiModelProperty(value = "${column.comment}")
    private String fid;
}

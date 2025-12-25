package com.center.medical.datamove.oracle.bean.model;


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
 * (QxWsLog)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:02
 */
@Data
@TableName("QX_WS_LOG")
@ApiModel(value = "QxWsLog", description = "$tableInfo.comment实体类")
public class QxWsLog extends Model<QxWsLog> implements Serializable {
    private static final long serialVersionUID = -23102035913972450L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String czUser;

    @ApiModelProperty(value = "${column.comment}")
    private Date czDate;

    @ApiModelProperty(value = "${column.comment}")
    private String djType;

    @ApiModelProperty(value = "${column.comment}")
    private String czType;

    @ApiModelProperty(value = "${column.comment}")
    private String czObject;

    @ApiModelProperty(value = "${column.comment}")
    private String ipDz;

    @ApiModelProperty(value = "${column.comment}")
    private String pcName;

    @ApiModelProperty(value = "${column.comment}")
    private String rmk;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String czdj;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;
}

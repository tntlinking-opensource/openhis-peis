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
 * 日志(QxLog)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:46
 */
@Data
@TableName("QX_LOG")
@ApiModel(value = "QxLog", description = "日志实体类")
public class QxLog extends Model<QxLog> implements Serializable {
    private static final long serialVersionUID = 104614119871522517L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "操作用户")
    private String czUser;

    @ApiModelProperty(value = "操作日期")
    private Date czDate;

    @ApiModelProperty(value = "单据类型")
    private String djType;

    @ApiModelProperty(value = "操作类型")
    private String czType;

    @ApiModelProperty(value = "操作对象")
    private String czObject;

    @ApiModelProperty(value = "IP地址")
    private String ipDz;

    @ApiModelProperty(value = "电脑名称")
    private String pcName;

    @ApiModelProperty(value = "操作说明")
    private String rmk;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "操作单据")
    private String czdj;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;
}

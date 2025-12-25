package com.center.medical.sync.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.SyncDataType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 同步数据操作记录(SyncDataLog)表实体类
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:32
 */
@Data
@TableName("md_sync_data_log")
@ApiModel(value = "SyncDataLog", description = "同步数据操作记录实体类")
public class SyncDataLog extends Model<SyncDataLog> implements Serializable {
    private static final long serialVersionUID = -42219464519830262L;

    @TableId(value = "logId")
    @ApiModelProperty(value = "同步记录ID")
    private Long logId;

    @ApiModelProperty(value = "同步数据操作ID")
    private Integer dataId;

    /**
     * @see SyncDataType
     */
    @ApiModelProperty(value = "数据类型")
    private Integer dataType;

    @ApiModelProperty(value = "操作类型：1.新增 2.更新 3.删除")
    private Integer optType;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "同步备注")
    private String remark;

    @ApiModelProperty(value = "是否成功：0.失败 1.成功")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}

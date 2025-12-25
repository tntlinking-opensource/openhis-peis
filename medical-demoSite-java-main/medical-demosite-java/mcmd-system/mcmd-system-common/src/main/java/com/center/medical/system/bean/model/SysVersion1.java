package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 自动部署-更新版本信息(SysVersion1)表实体类
 *
 * @author makejava
 * @since 2024-01-23 10:36:16
 */
@Data
@TableName("sys_version")
@ApiModel(value = "SysVersion1", description = "自动部署-更新版本信息实体类")
public class SysVersion1 extends Model<SysVersion1> implements Serializable {
    private static final long serialVersionUID = 216544030146944906L;

    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "系统服务ID（关联数据表sys_service_type的ID）")
    private Integer serviceId;

    @ApiModelProperty(value = "新版本安装包文件")
    private String filePath;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "执行方式：0.定期执行 1.立即执行 2.手动执行")
    private Integer executeType;

    @ApiModelProperty(value = "更新执行更新时间")
    private Date executeTime;

    @ApiModelProperty(value = "版本状态：-1失效 0.待更新 1.已更新部分 2.全部已更新")
    private Integer status;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "数据库更新文件")
    private String sqlFile;

    @ApiModelProperty(value = "创建者")
    private String createby;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新者")
    private String modifyby;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}

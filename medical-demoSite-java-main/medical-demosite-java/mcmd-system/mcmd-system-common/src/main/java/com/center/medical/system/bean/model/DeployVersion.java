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
 * 自动部署-更新版本信息(DeployVersion)表实体类
 *
 * @author makejava
 * @since 2023-11-15 08:42:31
 */
@Data
@TableName("sys_deploy_version")
@ApiModel(value = "DeployVersion", description = "版本信息实体类")
public class DeployVersion extends Model<DeployVersion> implements Serializable {
    private static final long serialVersionUID = 843868350316840400L;

    @TableId(value = "id")
    @ApiModelProperty(value = "版本信息id")
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "版本号(版本号最大的是最新版本)")
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "要求更新时间")
    private Date executeTime;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "更新类型 详见com.center.medical.bean.enums.DeployType")
    private Integer updateType;

    @ApiModelProperty(value = "更新包路径")
    private String filePath;

    @ApiModelProperty(value = "数据库更新语句")
    private String updateSql;

    @ApiModelProperty(value = "是否删除 0否 1是")
    private Boolean isDelete;

    @ApiModelProperty(value = "修改者user_name")
    private String modifier;

    @ApiModelProperty(value = "创建者user_name")
    private String creator;

    @ApiModelProperty(value = "是否启用 0否 1是")
    private Boolean isEnable;

}

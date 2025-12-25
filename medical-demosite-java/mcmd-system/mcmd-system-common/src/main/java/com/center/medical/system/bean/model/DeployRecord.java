package com.center.medical.system.bean.model;


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
 * 自动部署-科室更新状态(DeployRecord)表实体类
 *
 * @author makejava
 * @since 2023-11-15 08:28:43
 */
@Data
@TableName("sys_deploy_record")
@ApiModel(value = "DeployRecord", description = "自动部署-更新记录实体类")
public class DeployRecord extends Model<DeployRecord> implements Serializable {
    private static final long serialVersionUID = 411130193295964924L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否更新成功 0失败 1成功")
    private Boolean isSuccess;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //sys_deploy_version.id
    @ApiModelProperty(value = "版本信息id")
    private Integer versionId;

    //md_ks_ip.id
    @ApiModelProperty(value = "科室id")
    private String ksIpId;

    @ApiModelProperty(value = "错误信息")
    private String message;

    @ApiModelProperty(value = "ip")
    private String address;

    @ApiModelProperty(value = "主机名")
    private String hostName;

    @ApiModelProperty(value = "分中心编码")
    private String branchId;

    @ApiModelProperty(value = "创建人用户名（人工处理）")
    private String creator;

    @ApiModelProperty(value = "修改人用户名（人工处理）")
    private String modifier;

    @ApiModelProperty(value = "备注（人工处理）")
    private String remark;

    @ApiModelProperty(value = "人工处理结果 0失败 1成功")
    private Boolean isManualSuccess;

    @ApiModelProperty(value = "人工处理时间")
    private Date manualUpdateTime;

    @ApiModelProperty(value = "是否人工处理  1是0否")
    private Boolean isManual;

}

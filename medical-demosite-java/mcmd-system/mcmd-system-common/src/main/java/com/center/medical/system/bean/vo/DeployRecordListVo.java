package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 根据版本信息id查询各分中心更新状态
 * @author xhp
 * @since 2023-12-01 10:40
 */
@Data
@ApiModel(value = "DeployRecord", description = "自动部署-更新记录实体类")
public class DeployRecordListVo {
//    @ApiModelProperty(value = "更新记录id")
//    private String id;
//
//    @ApiModelProperty(value = "更新记录创建时间")
//    private Date createdate;
//
//    @ApiModelProperty(value = "更新记录修改时间")
//    private Date modifydate;

    @ApiModelProperty(value = "是否自动更新成功 0失败 1成功 -1未更新")
    private Boolean isSuccess;

    @ApiModelProperty(value = "自动更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "自动更新错误信息")
    private String message;

    @ApiModelProperty(value = "ip")
    private String address;

    @ApiModelProperty(value = "主机名")
    private String hostName;

//    @ApiModelProperty(value = "创建人用户名（人工处理）")
//    private String creator;

    @ApiModelProperty(value = "人工处理人用户名")
    private String modifier;

//    @ApiModelProperty(value = "备注（人工处理）")
//    private String remark;

//    @ApiModelProperty(value = "人工处理结果 0失败 1成功 null没有人工处理")
//    private Boolean isManualSuccess;

    @ApiModelProperty(value = "人工处理时间")
    private Date manualUpdateTime;

    @ApiModelProperty(value = "是否人工处理  1是0否")
    private Boolean isManual;

    @ApiModelProperty(value = "科室名称")
    private String depName;

    @ApiModelProperty(value = "是否开启自动更新 0否 1是")
    private Boolean isEnableUpdate;

    @ApiModelProperty(value = "更新服务启动命令")
    private String deployStartupCommand;

    @ApiModelProperty(value = "服务启动命令")
    private String serviceStartupCommand;

    @ApiModelProperty(value = "更新服务地址")
    private String deployServiceAddr;

    @ApiModelProperty(value = "被更新服务地址")
    private String serviceAddr;

    @ApiModelProperty(value = "科室id")
    private String ksIpId;
}

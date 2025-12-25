package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 科室IP(KsIp)表实体类
 *
 * @author 路飞船长
 * @since 2023-04-20 19:37:55
 */
@Data
@TableName("md_ks_ip")
@ApiModel(value = "KsIp", description = "科室IP实体类")
public class KsIp extends Model<KsIp> implements Serializable {
    private static final long serialVersionUID = 107035837610266921L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室名称")
    private String name;

    @ApiModelProperty(value = "IP地址：IP+端口号")
    private String ip;

    @ApiModelProperty(value = "关联的科室ID")
    private String ksId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "科室本地图片存储路径")
    private String localPath;

    @ApiModelProperty(value = "科室本地备份路径")
    private String backupPath;

    @ApiModelProperty(value = "状态：0.关闭 1.启用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}

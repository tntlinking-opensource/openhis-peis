package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础附件配置(BaseAttachmentConfig)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_base_attachment_config")
@ApiModel(value = "BaseAttachmentConfig", description = "基础附件配置实体类")
public class BaseAttachmentConfig extends Model<BaseAttachmentConfig> implements Serializable {
    private static final long serialVersionUID = 320241872704931548L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "AETITLE")
    private String aetitle;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "端口")
    private Integer port;

    @ApiModelProperty(value = "实际存储路径")
    private String realPath;

    @ApiModelProperty(value = "访问路径")
    private String visitPath;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "备注a")
    private String memoA;

    @ApiModelProperty(value = "备注b")
    private String memoB;

    @ApiModelProperty(value = "标示//如果是pacs，如果记录configid为null，获取flag最大的配置，保存新记录时也保存flag最大的id；如果不是pacs，没有configid时，取配置文件")
    private Integer flag;

    @ApiModelProperty(value = "映射路径")
    private String mappingPath;
}

package com.center.medical.datamove.common.bean.model;


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
 * (MdAttachmentConfig)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:07
 */
@Data
@TableName("md_attachment_config")
@ApiModel(value = "MdAttachmentConfig", description = "$tableInfo.comment实体类")
public class MdAttachmentConfig extends Model<MdAttachmentConfig> implements Serializable {
    private static final long serialVersionUID = 529942359939001508L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "aetitle")
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

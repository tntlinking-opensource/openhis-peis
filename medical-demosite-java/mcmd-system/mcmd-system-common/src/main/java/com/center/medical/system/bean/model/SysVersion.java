package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 版本控制-版本信息(SysVersion)表实体类
 *
 * @author makejava
 * @since 2024-03-01 18:02:36
 */
@Data
@TableName("sys_version")
@ApiModel(value = "SysVersion", description = "版本控制-版本信息实体类")
public class SysVersion extends Model<SysVersion> implements Serializable {
    private static final long serialVersionUID = 627612893837430655L;

    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "版本名称")
    private String versionName;

    @ApiModelProperty(value = "版本描述")
    private String notes;

    @ApiModelProperty(value = "版本标识：0.以前版本 1.当前版本 2.草稿版本")
    private Integer versionFlag;

    @ApiModelProperty(value = "版本状态：-1失效 0.待更新 1.已更新部分 2.全部已更新")
    private Integer status;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "创建者")
    private String createby;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新者")
    private String modifyby;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "更新小项列表")
    @TableField(exist = false)
    private List<SysVersionItem> versionItemList;
}

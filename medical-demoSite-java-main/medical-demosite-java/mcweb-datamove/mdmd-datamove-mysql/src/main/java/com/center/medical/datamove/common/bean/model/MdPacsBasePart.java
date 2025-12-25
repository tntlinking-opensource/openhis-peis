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
 * PACS-部位(MdPacsBasePart)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
@Data
@TableName("md_pacs_base_part")
@ApiModel(value = "MdPacsBasePart", description = "PACS-部位实体类")
public class MdPacsBasePart extends Model<MdPacsBasePart> implements Serializable {
    private static final long serialVersionUID = -29573896247831381L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "部位名称")
    private String partName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "排序")
    private Integer partSort;

    @ApiModelProperty(value = "pid")
    private String pid;

    @ApiModelProperty(value = "英文名称")
    private String enName;
}

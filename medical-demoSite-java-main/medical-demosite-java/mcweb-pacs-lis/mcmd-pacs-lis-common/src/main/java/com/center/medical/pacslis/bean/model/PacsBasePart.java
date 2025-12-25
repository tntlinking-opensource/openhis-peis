package com.center.medical.pacslis.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-部位(PacsBasePart)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_base_part")
@ApiModel(value = "PacsBasePart", description = "PACS-部位实体类")
public class PacsBasePart extends Model<PacsBasePart> implements Serializable {
    private static final long serialVersionUID = 212789088975041032L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

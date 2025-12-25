package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PACS-收费项目(PacsItems)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_pacs_items")
@ApiModel(value = "PacsItems", description = "PACS-收费项目实体类")
public class PacsItems extends Model<PacsItems> implements Serializable {
    private static final long serialVersionUID = -10751418284934710L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "是否启用")
    private String sysmanmark;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "收费项目名称缩写")
    private String examfeeitemNameabr;

    @ApiModelProperty(value = "收费项目打印名称")
    private String examfeeitemNameprn;

    @ApiModelProperty(value = "收费项目英语名称")
    private String examfeeitemNameeng;

    @ApiModelProperty(value = "收费项目英语名称缩写")
    private String examfeeitemNameengabr;

    @ApiModelProperty(value = "收费项目代码")
    private String examfeeitemCode;

    @ApiModelProperty(value = "部位IDs")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "创建人")
    private String xXxdm;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "打印排列序号")
    private String xh;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "性别 0男1女2通用")
    private String xb;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

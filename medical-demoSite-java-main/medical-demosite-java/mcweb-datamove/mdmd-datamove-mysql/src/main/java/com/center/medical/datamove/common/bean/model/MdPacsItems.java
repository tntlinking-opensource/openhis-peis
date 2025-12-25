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
 * PACS-收费项目(MdPacsItems)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
@Data
@TableName("md_pacs_items")
@ApiModel(value = "MdPacsItems", description = "PACS-收费项目实体类")
public class MdPacsItems extends Model<MdPacsItems> implements Serializable {
    private static final long serialVersionUID = 872745410820575430L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "部位ID")
    private String examfeeitemCodehm;

    @ApiModelProperty(value = "创建人")
    private String xXxdm;

    @ApiModelProperty(value = "所属科室ID")
    private String idDepart;

    @ApiModelProperty(value = "所属科室名称")
    private String departName;

    @ApiModelProperty(value = "收费项目输入码")
    private String sfxmsrm;

    @ApiModelProperty(value = "序号")
    private String xh;

    @ApiModelProperty(value = "检查意义")
    private String jcyy;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

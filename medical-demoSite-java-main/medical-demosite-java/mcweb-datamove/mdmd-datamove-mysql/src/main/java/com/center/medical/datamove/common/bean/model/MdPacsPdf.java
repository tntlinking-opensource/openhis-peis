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
 * PACS PDF  海康医院使用(MdPacsPdf)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
@Data
@TableName("md_pacs_pdf")
@ApiModel(value = "MdPacsPdf", description = "PACS PDF  海康医院使用实体类")
public class MdPacsPdf extends Model<MdPacsPdf> implements Serializable {
    private static final long serialVersionUID = 563363095561910804L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目id")
    private String feeitemId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "pdf地址")
    private String pdfUrl;
}

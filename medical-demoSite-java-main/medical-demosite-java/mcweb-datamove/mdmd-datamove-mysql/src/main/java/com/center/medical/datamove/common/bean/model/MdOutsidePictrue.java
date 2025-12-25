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
 * KS外送项目图片结果(MdOutsidePictrue)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
@Data
@TableName("md_outside_pictrue")
@ApiModel(value = "MdOutsidePictrue", description = "KS外送项目图片结果实体类")
public class MdOutsidePictrue extends Model<MdOutsidePictrue> implements Serializable {
    private static final long serialVersionUID = 374250009178222876L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "图片存放位置")
    private String pictruePosition;

    @ApiModelProperty(value = "序号")
    private Integer orderIndex;

    @ApiModelProperty(value = "收费项目ID(多个 逗号分隔)")
    private String chargeId;
}

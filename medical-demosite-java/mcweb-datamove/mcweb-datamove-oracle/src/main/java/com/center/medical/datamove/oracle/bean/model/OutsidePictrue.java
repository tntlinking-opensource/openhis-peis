package com.center.medical.datamove.oracle.bean.model;


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
 * KS外送项目图片结果(OutsidePictrue)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:35
 */
@Data
@TableName("OUTSIDE_PICTRUE")
@ApiModel(value = "OutsidePictrue", description = "KS外送项目图片结果实体类")
public class OutsidePictrue extends Model<OutsidePictrue> implements Serializable {
    private static final long serialVersionUID = 454478796207987250L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
    private Double orderIndex;

    @ApiModelProperty(value = "收费项目ID(多个 逗号分隔)")
    private String chargeId;
}

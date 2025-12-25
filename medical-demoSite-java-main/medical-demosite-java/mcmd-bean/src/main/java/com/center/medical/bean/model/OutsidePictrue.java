package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS外送项目图片结果(OutsidePictrue)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:08
 */
@Data
@TableName("md_outside_pictrue")
@ApiModel(value = "OutsidePictrue", description = "KS外送项目图片结果实体类")
public class OutsidePictrue extends Model<OutsidePictrue> implements Serializable {
    private static final long serialVersionUID = -95532556677290102L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @ApiModelProperty(value = "图片存放位置")
    private String pictruePosition;

    @ApiModelProperty(value = "序号")
    private Integer orderIndex;

    @ApiModelProperty(value = "收费项目ID(多个 逗号分隔)")
    private String chargeId;
}

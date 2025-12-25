package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS肺功能(Lung)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:07
 */
@Data
@TableName("md_lung")
@ApiModel(value = "Lung", description = "KS肺功能实体类")
public class Lung extends Model<Lung> implements Serializable {
    private static final long serialVersionUID = 509508402356650669L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "体检日期")
    private Date tjrq;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "小结")
    private String xj;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "用力肺活量(测定值)")
    private Double fvc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1)）")
    private Double fev;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)")
    private Double fevFvc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "最大呼气中期流速（MMEF %预测值）")
    private Double percentageMmef;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "最大呼气中期流速（MMEF 预测值）")
    private Double predictMmef;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "最大呼气中期流速（MMEF）")
    private Double mmef;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）%预测值")
    private Double percentageFeffa;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）预测值")
    private Double predictFeffa;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）")
    private Double feffa;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）%预测值")
    private Double percentageFeffb;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）预测值")
    private Double predictFeffb;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）")
    private Double feffb;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）%预测值")
    private Double percentageFeffc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）预测值")
    private Double predictFeffc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）")
    private Double feffc;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新日期")
    private Date modifydate;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "用力肺活量（预测值）")
    private Double predictFvc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "用力肺活量（%预测值）")
    private Double percentageFvc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1 预测值)")
    private Double predictFev;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1% 预测值)")
    private Double percentageFev;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)预测值")
    private Double predictFevFvc;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)%预测值")
    private Double percentageFevFvc;


    @TableField(exist = false)
    @ApiModelProperty(value = "是否弃检(弃用)")
    private String isUnchecked;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否危急值")
    private String isDanger;

    @TableField(exist = false)
    @ApiModelProperty(value = "上传文件的名称(肺功能上传全部使用)")
    private String origionName;
}

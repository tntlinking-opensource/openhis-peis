package com.center.medical.bean.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetLungVo implements Serializable {
    private static final long serialVersionUID = 7310236867094238455L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "体检日期")
    private Date tjrq;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "小结")
    private String xj;



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


    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    @ApiModelProperty(value = "检查人用户名")
    private String rummagerName;

    @ApiModelProperty(value = "审核者用户名")
    private String auditName;

    @ApiModelProperty(value = "健康小结")
    private String conclusions;

    @ApiModelProperty(value = "职业小结")
    private String zyConclusions;
}

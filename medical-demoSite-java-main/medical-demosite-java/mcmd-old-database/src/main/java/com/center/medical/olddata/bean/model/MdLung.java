package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * KS肺功能(MdLung)表实体类
 *
 * @author ay
 * @since 2024-06-05 16:02:44
 */
@Data
@TableName("md_lung")
@ApiModel(value = "MdLung", description = "KS肺功能实体类")
public class MdLung extends Model<MdLung> implements Serializable {
    private static final long serialVersionUID = 596110833348930212L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "科室ID")
    private String depId;


    @ApiModelProperty(value = "体检日期")
    private Date tjrq;


    @ApiModelProperty(value = "小结")
    private String xj;


    @ApiModelProperty(value = "创建人")
    private String createId;


    @ApiModelProperty(value = "更新人")
    private String modifyId;


    @ApiModelProperty(value = "用力肺活量(测定值)")
    private Double fvc;


    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1)）")
    private Double fev;


    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)")
    private Double fevFvc;


    @ApiModelProperty(value = "最大呼气中期流速（MMEF %预测值）")
    private Double percentageMmef;


    @ApiModelProperty(value = "最大呼气中期流速（MMEF 预测值）")
    private Double predictMmef;


    @ApiModelProperty(value = "最大呼气中期流速（MMEF）")
    private Double mmef;


    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）%预测值")
    private Double percentageFeffa;


    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）预测值")
    private Double predictFeffa;


    @ApiModelProperty(value = "75%呼气中期流速（FEF75%）")
    private Double feffa;


    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）%预测值")
    private Double percentageFeffb;


    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）预测值")
    private Double predictFeffb;


    @ApiModelProperty(value = "50%呼气中期流速（FEF50%）")
    private Double feffb;


    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）%预测值")
    private Double percentageFeffc;


    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）预测值")
    private Double predictFeffc;


    @ApiModelProperty(value = "25%呼气中期流速（FEF25%）")
    private Double feffc;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "创建日期")
    private Date createdate;


    @ApiModelProperty(value = "更新日期")
    private Date modifydate;


    @ApiModelProperty(value = "用力肺活量（预测值）")
    private Double predictFvc;


    @ApiModelProperty(value = "用力肺活量（%预测值）")
    private Double percentageFvc;


    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1 预测值)")
    private Double predictFev;


    @ApiModelProperty(value = "1秒钟用力肺活量（FEV1% 预测值)")
    private Double percentageFev;


    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)预测值")
    private Double predictFevFvc;


    @ApiModelProperty(value = "1秒用力呼气容积/用力肺活量(FEV1%G)%预测值")
    private Double percentageFevFvc;

}

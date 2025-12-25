package com.center.medical.bean.model;

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
 * KS动脉硬化结果(DmyhResult)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_dmyh_result")
@ApiModel(value = "DmyhResult", description = "KS动脉硬化结果实体类")
public class DmyhResult extends Model<DmyhResult> implements Serializable {
    private static final long serialVersionUID = 447089467996878430L;

    @ApiModelProperty(value = "RUXY")
    private String ruxy;

    @ApiModelProperty(value = "LUXY")
    private String luxy;

    @ApiModelProperty(value = "RDXY")
    private String rdxy;

    @ApiModelProperty(value = "LDXY")
    private String ldxy;

    @ApiModelProperty(value = "XL")
    private String xl;

    @ApiModelProperty(value = "RPWV")
    private String rpwv;

    @ApiModelProperty(value = "LPWV")
    private String lpwv;

    @ApiModelProperty(value = "RABI")
    private String rabi;

    @ApiModelProperty(value = "LABI")
    private String labi;

    @ApiModelProperty(value = "RTBI")
    private String rtbi;

    @ApiModelProperty(value = "LTBI")
    private String ltbi;

    @ApiModelProperty(value = "HEIGHT")
    private String height;

    @ApiModelProperty(value = "WEIGHT")
    private String weight;

    @ApiModelProperty(value = "SEX")
    private String sex;

    @ApiModelProperty(value = "TAI")
    private String tai;

    @ApiModelProperty(value = "TINFO")
    private String tinfo;

    @ApiModelProperty(value = "报告单号")
    private String tjrepno;

    @ApiModelProperty(value = "AGE")
    private String age;

    @ApiModelProperty(value = "C_DATE")
    private String cDate;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;
}

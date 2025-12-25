package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售财务录入表(Financeinput)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:59
 */
@Data
@TableName("md_financeinput")
@ApiModel(value = "Financeinput", description = "销售财务录入表实体类")
public class Financeinput extends Model<Financeinput> implements Serializable {
    private static final long serialVersionUID = -54378571417826653L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "1月")
    private String yy;

    @ApiModelProperty(value = "2月")
    private String ey;

    @ApiModelProperty(value = "3月")
    private String sy;

    @ApiModelProperty(value = "4月")
    private String iy;

    @ApiModelProperty(value = "5月")
    private String wy;

    @ApiModelProperty(value = "6月")
    private String ly;

    @ApiModelProperty(value = "7月")
    private String qy;

    @ApiModelProperty(value = "8月")
    private String ay;

    @ApiModelProperty(value = "9月")
    private String jy;

    @ApiModelProperty(value = "10月")
    private String oy;

    @ApiModelProperty(value = "11月")
    private String ny;

    @ApiModelProperty(value = "12月")
    private String dy;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}

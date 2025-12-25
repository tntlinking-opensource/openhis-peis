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
 * 财务录入表(Financeinput)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:44
 */
@Data
@TableName("FINANCEINPUT")
@ApiModel(value = "Financeinput", description = "财务录入表实体类")
public class Financeinput extends Model<Financeinput> implements Serializable {
    private static final long serialVersionUID = -21506030727217089L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}

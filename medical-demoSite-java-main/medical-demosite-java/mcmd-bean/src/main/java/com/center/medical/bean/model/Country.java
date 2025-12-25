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
 * JC国家(Country)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:57
 */
@Data
@TableName("md_country")
@ApiModel(value = "Country", description = "JC国家实体类")
public class Country extends Model<Country> implements Serializable {
    private static final long serialVersionUID = -78595522054561545L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "KEYCOUNTRY")
    private String keycountry;

    @ApiModelProperty(value = "国家名称")
    private String countryName;

    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    @ApiModelProperty(value = "COUNTRY_CODE2")
    private String countryCode2;

    @ApiModelProperty(value = "COUNTRY_CODE3")
    private String countryCode3;

    @ApiModelProperty(value = "COUNTRY_CODEHM")
    private String countryCodehm;

    @ApiModelProperty(value = "国家导出代码")
    private String countryCodexh;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}

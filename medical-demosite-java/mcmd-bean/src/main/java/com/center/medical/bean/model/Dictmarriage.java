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
 * 婚姻表(Dictmarriage)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_dictmarriage")
@ApiModel(value = "Dictmarriage", description = "婚姻表实体类")
public class Dictmarriage extends Model<Dictmarriage> implements Serializable {
    private static final long serialVersionUID = 732296207399868592L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "KEYMARRIAGE")
    private String keymarriage;

    @ApiModelProperty(value = "婚姻名称")
    private String marriageName;

    @ApiModelProperty(value = "婚姻代码")
    private String marriageCode;

    @ApiModelProperty(value = "MARRIAGE_CODE2")
    private String marriageCode2;

    @ApiModelProperty(value = "MARRIAGE_CODE3")
    private String marriageCode3;

    @ApiModelProperty(value = "MARRIAGE_CODEHM")
    private String marriageCodehm;

    @ApiModelProperty(value = "导出代码")
    private String marriageCodex;

    @ApiModelProperty(value = "视同已婚：0或null.否 1.是")
    private Integer fHasmarried;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "状态")
    private Integer status;
}

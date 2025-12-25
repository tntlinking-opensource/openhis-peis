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
 * 职业类型表(Dictoccupation)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_dictoccupation")
@ApiModel(value = "Dictoccupation", description = "职业类型表实体类")
public class Dictoccupation extends Model<Dictoccupation> implements Serializable {
    private static final long serialVersionUID = -36120898678190846L;

    @ApiModelProperty(value = "健管代码")
    private String keyOccupation;

    @ApiModelProperty(value = "职业名称")
    private String occupationName;

    @ApiModelProperty(value = "职业代码")
    private String occupationCode;

    @ApiModelProperty(value = "职业代码2")
    private String occupationCode2;

    @ApiModelProperty(value = "职业代码3")
    private String occupationCode3;

    @ApiModelProperty(value = "OCCUPATION_CODEHM")
    private String occupationCodehm;

    @ApiModelProperty(value = "导出代码")
    private String occupationcodex;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

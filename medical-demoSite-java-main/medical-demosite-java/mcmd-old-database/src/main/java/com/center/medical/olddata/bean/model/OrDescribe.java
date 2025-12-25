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
 * KS科室描述、检查结果表(Describe)表实体类
 *
 * @author ay
 * @since 2024-06-05 15:35:57
 */
@Data
@TableName("DESCRIBE")
@ApiModel(value = "Describe", description = "KS科室描述、检查结果表实体类")
public class OrDescribe extends Model<OrDescribe> implements Serializable {
    private static final long serialVersionUID = 916180682045548801L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @ApiModelProperty(value = "检查项目ID")
    private String itemId;


    @ApiModelProperty(value = "检查项目打印名称")
    private String itemName;


    @ApiModelProperty(value = "检查描述")
    private String inspectDescribe;


    @ApiModelProperty(value = "用于存放该检查项目下的所有体证词所拼接的字符串。")
    private String signList;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "科室ID")
    private String depId;


    @ApiModelProperty(value = "体检类型 1.职业 0.非职业")
    private Integer tjlx;


    @ApiModelProperty(value = "短号体检号")
    private Integer shortCode;


    @ApiModelProperty(value = "科室描述")
    private String depDescription;


    @ApiModelProperty(value = "收费项目Id")
    private String feeId;


    @ApiModelProperty(value = "收费项目打印名称")
    private String feeName;

}

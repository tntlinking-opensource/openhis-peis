package com.center.medical.datamove.common.bean.model;


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
 * 艾迪康项目代码表(MdBaseAdiconExamItemCode)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
@Data
@TableName("md_base_adicon_exam_item_code")
@ApiModel(value = "MdBaseAdiconExamItemCode", description = "艾迪康项目代码表实体类")
public class MdBaseAdiconExamItemCode extends Model<MdBaseAdiconExamItemCode> implements Serializable {
    private static final long serialVersionUID = -16607306027007188L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "项目代码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}

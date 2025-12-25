package com.center.medical.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新老系统体检号对应关系(CodeCorresponding)表实体类
 *
 * @author ay
 * @since 2023-08-26 13:54:11
 */
@Data
@TableName("md_code_corresponding")
@ApiModel(value = "CodeCorresponding", description = "新老系统体检号对应关系实体类")
public class CodeCorresponding extends Model<CodeCorresponding> implements Serializable {
    private static final long serialVersionUID = -65434501354858804L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "老系统体检码")
    private String oldCode;


    @ApiModelProperty(value = "新系统体检码")
    private String newCode;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    public CodeCorresponding(String oldCode, String newCode) {
        this.oldCode = oldCode;
        this.newCode = newCode;
    }


    public CodeCorresponding() {
    }
}

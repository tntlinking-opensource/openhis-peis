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
 * 工种(MdBaseWorktype)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Data
@TableName("md_base_worktype")
@ApiModel(value = "MdBaseWorktype", description = "工种实体类")
public class MdBaseWorktype extends Model<MdBaseWorktype> implements Serializable {
    private static final long serialVersionUID = 955423521429437036L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "工种名称")
    private String typeName;

    @ApiModelProperty(value = "青岛代码")
    private String qingdaoCode;

    @ApiModelProperty(value = "济南代码")
    private String jinanCode;

    @ApiModelProperty(value = "长沙代码")
    private String changshaCode;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

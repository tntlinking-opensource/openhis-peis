package com.center.medical.datamove.common.bean.model;


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
 * 存放文件路径表(MdSavefilepath)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:28
 */
@Data
@TableName("md_savefilepath")
@ApiModel(value = "MdSavefilepath", description = "存放文件路径表实体类")
public class MdSavefilepath extends Model<MdSavefilepath> implements Serializable {
    private static final long serialVersionUID = -84527763013125150L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "公共ID")
    private String ggid;

    @ApiModelProperty(value = "文件路径+文件名")
    private String filepath;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}

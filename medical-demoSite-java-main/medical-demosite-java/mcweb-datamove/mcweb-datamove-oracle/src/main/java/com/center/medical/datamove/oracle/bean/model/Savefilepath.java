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
 * 存放文件路径表(Savefilepath)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:38
 */
@Data
@TableName("SAVEFILEPATH")
@ApiModel(value = "Savefilepath", description = "存放文件路径表实体类")
public class Savefilepath extends Model<Savefilepath> implements Serializable {
    private static final long serialVersionUID = 612128021602567575L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

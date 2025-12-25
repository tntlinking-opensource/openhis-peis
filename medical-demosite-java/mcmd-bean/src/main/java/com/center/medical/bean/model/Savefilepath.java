package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 存放文件路径表(Savefilepath)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:11
 */
@Data
@TableName("md_savefilepath")
@ApiModel(value = "Savefilepath", description = "存放文件路径表实体类")
public class Savefilepath extends Model<Savefilepath> implements Serializable {
    private static final long serialVersionUID = -99155499774328347L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "公共ID")
    private String ggid;

    @ApiModelProperty(value = "文件来源：1.合同 2.企业环境监测报告")
    private Integer fileflag;

    @ApiModelProperty(value = "文件路径+文件名")
    private String filepath;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    public Savefilepath(String ggid, String filepath, Integer fileflag) {
        this.ggid = ggid;
        this.filepath = filepath;
        this.fileflag = fileflag;
    }

    public Savefilepath() {
    }
}

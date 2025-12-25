package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 销售打印分类设置(Printtype)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:38
 */
@Data
@TableName("PRINTTYPE")
@ApiModel(value = "Printtype", description = "销售打印分类设置实体类")
public class Printtype extends Model<Printtype> implements Serializable {
    private static final long serialVersionUID = 194663162370622693L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "打印项目分类名称")
    private String printName;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "删除")
    private Double isDelete;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "更改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "序号")
    private Double seq;

    @ApiModelProperty(value = "顺序 实际使用此字段排序")
    private Double shunxu;
}

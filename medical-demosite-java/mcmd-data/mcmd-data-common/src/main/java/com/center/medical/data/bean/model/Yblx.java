package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 样本类型(Yblx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:58
 */
@Data
@TableName("md_yblx")
@ApiModel(value = "Yblx", description = "样本类型实体类")
public class Yblx extends Model<Yblx> implements Serializable {
    private static final long serialVersionUID = 245686047288896088L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "样本名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "序号")
    private Integer xh;
}

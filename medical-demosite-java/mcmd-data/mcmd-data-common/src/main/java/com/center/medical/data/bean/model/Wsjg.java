package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 外送机构(Wsjg)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:57
 */
@Data
@TableName("md_wsjg")
@ApiModel(value = "Wsjg", description = "外送机构实体类")
public class Wsjg extends Model<Wsjg> implements Serializable {
    private static final long serialVersionUID = 305134908449723762L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

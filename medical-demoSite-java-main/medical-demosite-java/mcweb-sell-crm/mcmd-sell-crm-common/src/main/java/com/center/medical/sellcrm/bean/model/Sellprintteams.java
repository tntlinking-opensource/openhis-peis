package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售打印项目分类设置表(Sellprintteams)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:24
 */
@Data
@TableName("crm_sellprintteams")
@ApiModel(value = "Sellprintteams", description = "销售打印项目分类设置表实体类")
public class Sellprintteams extends Model<Sellprintteams> implements Serializable {
    private static final long serialVersionUID = -26045099090646182L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "打印项目分类名称")
    private String dyxmflmc;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

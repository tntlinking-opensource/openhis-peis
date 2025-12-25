package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单柜子信息(Chest)表实体类
 *
 * @author 路飞船长
 * @since 2022-12-06 08:57:26
 */
@Data
@TableName("md_chest")
@ApiModel(value = "Chest", description = "此表为通用表，团检实体类")
public class Chest extends Model<Chest> implements Serializable {
    private static final long serialVersionUID = 772701347975065741L;

    @Excel(name = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * @see ExamType
     */
    @Excel(name = "体检类型")
    @ApiModelProperty(value = "体检类型")
    private Integer tjlx;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "柜子号")
    @ApiModelProperty(value = "柜子号")
    private String gzh;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "单位名称")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "编号")
    private String chestNum;

    /**
     * full constructor
     */
    public Chest(Integer tjlx, String dwmc, String gzh, String note, String ddh) {
        this.tjlx = tjlx;
        this.dwmc = dwmc;
        this.gzh = gzh;
        this.note = note;
        this.ddh = ddh;
        this.isDelete = 0;
    }

    public Chest() {
    }
}

package com.center.medical.olddata.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单与套餐关联表(MdOrderandcombo)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:37:59
 */
@Data
@TableName("md_orderandcombo")
@ApiModel(value = "MdOrderandcombo", description = "订单与套餐关联表实体类")
public class MdOrderandcombo extends Model<MdOrderandcombo> implements Serializable {
    private static final long serialVersionUID = 795255329764381190L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String ddid;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "套餐状态--用于区分从哪张表中取套餐(0:普通套餐、1/2:最小套餐)")
    private String combostate;

    @ApiModelProperty(value = "0.否 1.是--前台需要")
    private Integer isjj;

    @ApiModelProperty(value = "0.否 1.是--前台需要")
    private String sfybd;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "审批状态：0.未审批 1.已审批")
    private Integer spzt;

    @ApiModelProperty(value = "是否变更：1是最近一次变更")
    private Integer isbg;

    @TableField(value = "`show`")
    @ApiModelProperty(value = "是否显示：0或者Null展示 1.隐藏")
    private Integer show;

    @ApiModelProperty(value = "检查类型，详见：com.world.center.bean.enums.ExamClass")
    private Integer idExamclass;
}

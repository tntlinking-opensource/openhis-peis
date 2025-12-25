package com.center.medical.olddata.bean.model;


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
 * 订单与套餐关联表(Orderandcombo)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:38:46
 */
@Data
@TableName("ORDERANDCOMBO")
@ApiModel(value = "Orderandcombo", description = "订单与套餐关联表实体类")
public class OrOrderandcombo extends Model<OrOrderandcombo> implements Serializable {
    private static final long serialVersionUID = 236995038466037967L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "0:否  1:是--前台需要")
    private Integer isjj;

    @ApiModelProperty(value = "是、否--前台需要")
    private String sfybd;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "审批状态 1已审批 0未审批")
    private Integer spzt;

    @ApiModelProperty(value = "1是最近一次变更")
    private Integer isbg;

    @ApiModelProperty(value = "${column.comment}")
    private Integer show;

    @ApiModelProperty(value = "${column.comment}")
    private Integer idExamclass;
}

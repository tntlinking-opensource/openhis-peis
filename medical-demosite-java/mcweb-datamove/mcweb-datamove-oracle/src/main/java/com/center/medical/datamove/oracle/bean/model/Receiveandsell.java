package com.center.medical.datamove.oracle.bean.model;


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
 * 客户公共池领取与领取人员关联表(Receiveandsell)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:14
 */
@Data
@TableName("RECEIVEANDSELL")
@ApiModel(value = "Receiveandsell", description = "客户公共池领取与领取人员关联表实体类")
public class Receiveandsell extends Model<Receiveandsell> implements Serializable {
    private static final long serialVersionUID = 801640815516829203L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户公共池ID")
    private String clientid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "操作日期")
    private Date lqrq;

    @ApiModelProperty(value = "假删状态")
    private Integer isDelete;

    @ApiModelProperty(value = "0：领取 1：分配")
    private Integer czzt;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

package com.center.medical.datamove.common.bean.model;


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
 * 销售打印项目分类设置表(CrmSellprintteams)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
@Data
@TableName("crm_sellprintteams")
@ApiModel(value = "CrmSellprintteams", description = "销售打印项目分类设置表实体类")
public class CrmSellprintteams extends Model<CrmSellprintteams> implements Serializable {
    private static final long serialVersionUID = 967725970862983133L;

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
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

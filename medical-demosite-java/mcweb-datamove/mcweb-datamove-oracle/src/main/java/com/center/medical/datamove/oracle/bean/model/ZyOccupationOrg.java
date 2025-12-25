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
 * JC职业病体检机构(ZyOccupationOrg)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:30:59
 */
@Data
@TableName("ZY_OCCUPATION_ORG")
@ApiModel(value = "ZyOccupationOrg", description = "JC职业病体检机构实体类")
public class ZyOccupationOrg extends Model<ZyOccupationOrg> implements Serializable {
    private static final long serialVersionUID = 336682908076951573L;

    @TableId(value = "ORG_CODE")
    @ApiModelProperty(value = "机构代码")
    private String orgCode;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "机构别名")
    private String orgOtherName;

    @ApiModelProperty(value = "详细信息")
    private String orgInfo;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "假删状态")
    private Double isDelete;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}

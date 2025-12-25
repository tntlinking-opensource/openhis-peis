package com.center.medical.bean.model;

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
 * JC职业病体检机构(ZyOccupationOrg)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:19
 */
@Data
@TableName("md_zy_occupation_org")
@ApiModel(value = "ZyOccupationOrg", description = "JC职业病体检机构实体类")
public class ZyOccupationOrg extends Model<ZyOccupationOrg> implements Serializable {
    private static final long serialVersionUID = -62471172740588216L;

    @TableId(value = "orgCode", type = IdType.ASSIGN_ID)
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

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}

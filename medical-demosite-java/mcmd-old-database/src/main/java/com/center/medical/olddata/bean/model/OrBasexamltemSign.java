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
 * JC检查项目体证词关联表(BasexamltemSign)表实体类
 *
 * @author ay
 * @since 2024-07-13 14:27:48
 */
@Data
@TableName("BASEXAMLTEM_SIGN")
@ApiModel(value = "BasexamltemSign", description = "JC检查项目体证词关联表实体类")
public class OrBasexamltemSign extends Model<OrBasexamltemSign> implements Serializable {
    private static final long serialVersionUID = 262824772059431338L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = ".330")
    private String id;


    @ApiModelProperty(value = "检查项目ID")
    private String inspectId;


    @ApiModelProperty(value = "体证词名称")
    private String name;


    @ApiModelProperty(value = "结论词ID")
    private String resultId;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "更新时间")
    private Date modifydate;


    @ApiModelProperty(value = "是否进小结")
    private Integer isInSummary;


    @ApiModelProperty(value = "体证词输入码")
    private String bodyInputCode;


    @ApiModelProperty(value = "体证词详细描述")
    private String bodyDetail;


    @ApiModelProperty(value = "体证词详细描述(职业病）")
    private String bodyDetailZy;


    @ApiModelProperty(value = "重症级别")
    private Integer intensiveLevel;


    @ApiModelProperty(value = "互斥分组（异组互斥）")
    private String otherMutex;


    @ApiModelProperty(value = "互斥分组（同组正整数编号互斥）")
    private Object numMutex;


    @ApiModelProperty(value = "是否阳性")
    private Integer isPositive;


    @ApiModelProperty(value = "输入码")
    private String inputCode;


    @ApiModelProperty(value = "0:未删除 1：删除")
    private Integer isDelete;


    @ApiModelProperty(value = "0:未选中 1：选中")
    private Integer isDefault;


    @ApiModelProperty(value = "体征词显示顺序，1、2、3、4...")
    private Integer orderindex;


    @ApiModelProperty(value = "${column.comment}")
    private String isInput;

}

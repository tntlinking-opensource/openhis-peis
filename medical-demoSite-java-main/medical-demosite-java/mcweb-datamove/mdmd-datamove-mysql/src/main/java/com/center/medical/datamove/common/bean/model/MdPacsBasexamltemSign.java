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
 * PACS-体征词(MdPacsBasexamltemSign)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
@Data
@TableName("md_pacs_basexamltem_sign")
@ApiModel(value = "MdPacsBasexamltemSign", description = "PACS-体征词实体类")
public class MdPacsBasexamltemSign extends Model<MdPacsBasexamltemSign> implements Serializable {
    private static final long serialVersionUID = 625194202856404311L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "检查项目ID")
    private String inspectId;

    @ApiModelProperty(value = "体征词名称")
    private String name;

    @ApiModelProperty(value = "结论词")
    private String resultId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "不进小结：0代表进，1代表不进")
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
    private Integer numMutex;

    @ApiModelProperty(value = "是否阳性结果：0或null.否 1.是")
    private Integer isPositive;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否选中：0.不选中 1.选中")
    private Integer isDefault;

    @ApiModelProperty(value = "体征词显示顺序")
    private Integer orderindex;

    @ApiModelProperty(value = "is_input")
    private String isInput;
}

package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * JC检查项目类型表(Basexamltemtype)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:55
 */
@Data
@TableName("md_basexamltemtype")
@ApiModel(value = "Basexamltemtype", description = "JC检查项目类型表实体类")
public class Basexamltemtype extends Model<Basexamltemtype> implements Serializable {
    private static final long serialVersionUID = -36085316501536559L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "EXAMITEMTYPEKEY")
    private String examitemtypekey;

    @ApiModelProperty(value = "检查项目类型名称")
    private String examitemtypeName;

    @ApiModelProperty(value = "检查项目类型英文名称")
    private String examitemtypeNameeng;

    @ApiModelProperty(value = "检查项目类型代码")
    private String examitemtypeCode;

    @ApiModelProperty(value = "导出代码")
    private String examitemtypecodex;

    @ApiModelProperty(value = "部门ID")
    private String idDepartment;

    @ApiModelProperty(value = "部门名称(R)")
    private String departR;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

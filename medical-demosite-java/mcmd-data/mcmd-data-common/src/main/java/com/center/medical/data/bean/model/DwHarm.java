package com.center.medical.data.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 单位危害因素(DwHarm)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_dw_harm")
@ApiModel(value = "DwHarm", description = "单位危害因素实体类")
public class DwHarm extends Model<DwHarm> implements Serializable {
    private static final long serialVersionUID = -45076810093765395L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID号")
    private String id;

    @ApiModelProperty(value = "危害因素代码")
    private String harmCode;

    @ApiModelProperty(value = "危害因素名字")
    private String harmName;

    @ApiModelProperty(value = "种类名字")
    private String harmClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    public String getInputCode() {
        if (StringUtils.isEmpty(inputCode)) {
            return null;
        }
        return inputCode.toUpperCase();
    }

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "KEYWORD")
    private String keyword;

    @ApiModelProperty(value = "诊断结论(依据)")
    private String diagnosisFrom;

    @ApiModelProperty(value = "MBJB_ZYB")
    private String mbjbZyb;

    @ApiModelProperty(value = "MBJB_JJZ")
    private String mbjbJjz;

    @ApiModelProperty(value = "影响")
    private String influence;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "诊断依据")
    private String diagnosis;

    @ApiModelProperty(value = "职业病")
    private String industrialDisease;

    @ApiModelProperty(value = "禁忌症")
    private String contraindication;

    @ApiModelProperty(value = "对健康影响")
    private String affect;

    @ApiModelProperty(value = "客户公司名称")
    private String companyName;

    @ApiModelProperty(value = "客户公司ID")
    private String companyId;

    @ApiModelProperty(value = "公司部门")
    private String companyDepartment;

    @ApiModelProperty(value = "公司部门ID")
    private String departmentId;

    @ApiModelProperty(value = "危害因素种类ID")
    private String classId;

    @ApiModelProperty(value = "单位代码")
    private String khdwsrm;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;
}

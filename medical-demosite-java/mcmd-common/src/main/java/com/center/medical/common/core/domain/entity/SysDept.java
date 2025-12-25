package com.center.medical.common.core.domain.entity;

import com.center.medical.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门表 sys_dept
 *
 * @author 路飞
 */
@Data
@ApiModel(value = "部门", description = "部门实体类")
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    /**
     * 部门编号，对应原体检系统部门id
     */
    @ApiModelProperty(value = "部门编号，对应原体检系统部门id")
    private String deptNo;

    /**
     * 父部门ID
     */
    @ApiModelProperty(value = "父部门ID")
    private Long parentId;

    /**
     * @see com.center.medical.bean.enums.ksType
     */
    @ApiModelProperty(value = "科室类型：0.常规 1.PACS科室 2.Lis科室")
    private Integer ksType;

    /**
     * 祖级列表
     */
    @ApiModelProperty(value = "祖级列表")
    private String ancestors;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "英文名")
    private String englishName;


    @ApiModelProperty(value = "是否为功能部门0为非功能科室1为功能科室")
    private String isFunction;


    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    /**
     * 数据报告格式，详见：
     *
     * @see com.center.medical.bean.enums.SjbggsType
     */
    @ApiModelProperty(value = "数据报告格式：1.检验科检查、2.外送项目检查、3.一般检查、4.C13检查、5:肺功能检查、6.无图像检查、7.图像检查、8.问诊检查、9.电测听检查、10.骨密度检查、11.视力检查、12.健康检查")
    private Integer sjbggs;


    @ApiModelProperty(value = "检查地点")
    private String jcdd;

    /**
     * 接口类型
     *
     * @see com.center.medical.bean.enums.LpsJklxType
     */
    @ApiModelProperty(value = "接口类型：HIS、LIS、DR、US、CR、CT、EYE、MR")
    private String jklx;


    @ApiModelProperty(value = "科室号")
    private String ksh;


    @ApiModelProperty(value = "图片地址")
    private String imgpath;


    @ApiModelProperty(value = "健康报告默认模板存放路径")
    private String reportPathHealth;


    @ApiModelProperty(value = "职业报告默认模板存放路径")
    private String reportPathDisease;


    @ApiModelProperty(value = "是否最后追加图片：0否 1.是")
    private String addPicBefore;


    @ApiModelProperty(value = "科室报告排序")
    private Integer reportSort;


    @ApiModelProperty(value = "导引单排序")
    private Integer dydXh;


    @ApiModelProperty(value = "导引单备注")
    private String dydMemo;


    @ApiModelProperty(value = "金蝶号码")
    private String kingdeeAccountNo;


    @ApiModelProperty(value = "灰色图路径")
    private String greyImgpath;


    @ApiModelProperty(value = "是否在APP显示：0否 1.是")
    private String isShowapp;


    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    @ApiModelProperty(value = "部门状态:0正常,1停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    /**
     * 父部门名称
     */
    @ApiModelProperty(value = "父部门名称")
    private String parentName;

    /**
     * 子部门
     */
    @ApiModelProperty(value = "子部门")
    private List<SysDept> children = new ArrayList<SysDept>();


    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deptId", getDeptId())
                .append("parentId", getParentId())
                .append("ancestors", getAncestors())
                .append("deptName", getDeptName())
                .append("orderNum", getOrderNum())
                .append("leader", getLeader())
                .append("phone", getPhone())
                .append("email", getEmail())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}

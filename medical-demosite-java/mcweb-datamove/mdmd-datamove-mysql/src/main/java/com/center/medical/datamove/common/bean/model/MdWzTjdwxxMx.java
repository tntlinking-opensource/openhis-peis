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
 * KS问诊——单位明细信息(MdWzTjdwxxMx)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
@Data
@TableName("md_wz_tjdwxx_mx")
@ApiModel(value = "MdWzTjdwxxMx", description = "KS问诊——单位明细信息实体类")
public class MdWzTjdwxxMx extends Model<MdWzTjdwxxMx> implements Serializable {
    private static final long serialVersionUID = 329922398659924335L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "序号")
    private String id;

    @ApiModelProperty(value = "登记流水")
    private String djls;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检次数")
    private Integer amount;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "基本工艺过程")
    private String basicCourse;

    @ApiModelProperty(value = "防护设施和防护用品的管理")
    private String defendManage;

    @ApiModelProperty(value = "劳动条件")
    private String workCondition;

    @ApiModelProperty(value = "危害因素检测")
    private String diathesisCheck;

    @ApiModelProperty(value = "操作员")
    private String dbUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "单位编码")
    private String corpCode;

    @ApiModelProperty(value = "单位名称")
    private String corpName;

    @ApiModelProperty(value = "单位地址")
    private String corpAddress;

    @ApiModelProperty(value = "邮编")
    private String post;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}

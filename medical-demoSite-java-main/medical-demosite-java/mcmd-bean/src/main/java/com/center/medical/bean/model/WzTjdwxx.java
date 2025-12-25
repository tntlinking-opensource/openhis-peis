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
 * KS问诊——体检单位信息(WzTjdwxx)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:18
 */
@Data
@TableName("md_wz_tjdwxx")
@ApiModel(value = "WzTjdwxx", description = "KS问诊——体检单位信息实体类")
public class WzTjdwxx extends Model<WzTjdwxx> implements Serializable {
    private static final long serialVersionUID = 699422942038888098L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "日期")
    private Date createDate;

    @ApiModelProperty(value = "单位编码")
    private String corpCode;

    @ApiModelProperty(value = "单位名称")
    private String corpName;

    @ApiModelProperty(value = "地址")
    private String corpAddress;

    @ApiModelProperty(value = "邮编")
    private String post;

    @ApiModelProperty(value = "ECONOMY_TYPE")
    private String economyType;

    @ApiModelProperty(value = "CALLING")
    private String calling;

    @ApiModelProperty(value = "CALLING_SPACE")
    private String callingSpace;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "联系人")
    private String dbUser;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "LXR")
    private String lxr;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "更新人")
    private String modifyId;
}

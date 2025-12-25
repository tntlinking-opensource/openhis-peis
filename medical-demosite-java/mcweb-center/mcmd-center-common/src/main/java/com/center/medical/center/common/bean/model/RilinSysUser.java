package com.center.medical.center.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息表(SysUser)表实体类
 *
 * @author makejava
 * @since 2025-03-23 15:23:07
 */
@Data
@TableName("sys_user")
@ApiModel(value="SysUser",description="用户信息表实体类")
public class RilinSysUser extends Model<RilinSysUser> implements Serializable {
	private static final long serialVersionUID = 499412161956923314L;

    @TableId(value="user_id")
    @ApiModelProperty(value = "用户ID")
	private Long userId;

    @ApiModelProperty(value = "用户编号")
	private String userNo;

    @ApiModelProperty(value = "部门ID")
	private Long deptId;

    @ApiModelProperty(value = "用户账号")
	private String userName;

    @ApiModelProperty(value = "用户昵称")
	private String nickName;

    @ApiModelProperty(value = "用户类型（00系统用户）")
	private String userType;

    @ApiModelProperty(value = "用户邮箱")
	private String email;

    @ApiModelProperty(value = "手机号码")
	private String phonenumber;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
	private String sex;

    @ApiModelProperty(value = "头像地址")
	private String avatar;

    @ApiModelProperty(value = "密码")
	private String password;

    @ApiModelProperty(value = "销售折扣")
	private Double sdiscount;

    @ApiModelProperty(value = "领导折扣")
	private Double ldiscount;

    @ApiModelProperty(value = "是否领导：0.否 1.是)")
	private String isleader;

    @ApiModelProperty(value = "分中心id")
	private String cid;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
	private String status;

    @ApiModelProperty(value = "输入码")
	private String inputCode;

    @ApiModelProperty(value = "交接密码")
	private String reciveCode;

    @ApiModelProperty(value = "是否是医师：0.否 1.是")
	private String isDoc;

    @ApiModelProperty(value = "接口库用户代码（lis代码）")
	private String userCode;

    @ApiModelProperty(value = "医生照片")
	private String picture;

    @ApiModelProperty(value = "医生签字")
	private String signPic;

    @ApiModelProperty(value = "上级id（销售，只有一层）")
	private String superiorId;

    @ApiModelProperty(value = "是否补检账号：0.不是 1.是）")
	private BigDecimal isbj;

    @ApiModelProperty(value = "涉密密码（家庭卡）")
	private String secretPassword;

    @ApiModelProperty(value = "是否删除：0.存在 1.删除）")
	private String delFlag;

    @ApiModelProperty(value = "最后登录IP")
	private String loginIp;

    @ApiModelProperty(value = "最后登录时间")
	private Date loginDate;

    @ApiModelProperty(value = "创建者")
	private String createBy;

    @ApiModelProperty(value = "创建时间")
	private Date createTime;

    @ApiModelProperty(value = "更新者")
	private String updateBy;

    @ApiModelProperty(value = "更新时间")
	private Date updateTime;

    @ApiModelProperty(value = "用户密码修改日期")
	private Date expDate;

    @ApiModelProperty(value = "禁用时间")
	private Date lockDate;

    @ApiModelProperty(value = "解禁时间")
	private Date unlockDate;

    @ApiModelProperty(value = "备注")
	private String remark;

    @ApiModelProperty(value = "金蝶账户ID")
	private String kingdeeUseStatus;

    @ApiModelProperty(value = "金蝶账户状态")
	private String kingdeeAccountNo;

    @ApiModelProperty(value = "${column.comment}")
	private Date entryDate;
}

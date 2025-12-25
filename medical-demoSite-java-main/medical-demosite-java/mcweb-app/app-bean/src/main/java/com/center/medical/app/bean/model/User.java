package com.center.medical.app.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表(User)表实体类
 *
 * @author 路飞船长
 * @since 2023-03-27 16:53:08
 */
@Data
@TableName("md_user")
@ApiModel(value = "User", description = "用户表实体类")
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = 757161932563414991L;

    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value = "ID")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户邮箱")
    private String userMail;

    @ApiModelProperty(value = "登录密码")
    private String loginPassword;

    @ApiModelProperty(value = "支付密码")
    private String payPassword;

    @ApiModelProperty(value = "账号登陆使用的账号")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    private String userMobile;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    @ApiModelProperty(value = "注册时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userRegtime;

    @ApiModelProperty(value = "注册IP")
    private String userRegip;

    @ApiModelProperty(value = "备注")
    private String userMemo;

    @ApiModelProperty(value = "性别：0.男 1.女")
    private Integer sex;

    @ApiModelProperty(value = "例如：2009-11-27")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @ApiModelProperty(value = "头像图片路径")
    private String pic;

    @ApiModelProperty(value = "状态 1 正常 0 无效")
    private Integer status;

    @ApiModelProperty(value = "会员等级（冗余字段）")
    private Long level;

    @ApiModelProperty(value = "vip结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vipEndTime;

    @ApiModelProperty(value = "等级条件 0 普通会员 1 付费会员")
    private Integer levelType;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区/县")
    private String area;

    @ApiModelProperty(value = "省代码")
    private String provinceId;

    @ApiModelProperty(value = "市代码")
    private String cityId;

    @ApiModelProperty(value = "区县代码")
    private String areaId;

    @ApiModelProperty(value = "兴趣类型")
    private String tags;

    /**
     * openId list
     */
    @TableField(exist = false)
    private List<String> bizUserIdList;
}

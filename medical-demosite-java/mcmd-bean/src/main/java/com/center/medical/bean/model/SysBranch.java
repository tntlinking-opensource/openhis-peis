package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分中心管理对象 sys_branch
 *
 * @author 路飞
 * @date 2022-10-21
 */
@Data
@TableName("sys_branch")
@ApiModel(value = "SysBranch", description = "分中心管理实体类")
public class SysBranch extends Model<SysBranch> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分中心id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "分中心id")
    private Integer id;

    /**
     * 分中心编码
     */
    @ApiModelProperty(value = "分中心编码")
    private String branchId;

    /**
     * 分中心名字
     */
    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    /**
     * 简码，不能重复
     */
    @ApiModelProperty(value = "简码，不能重复")
    private String jm;

    /**
     * 输入码
     */
    @ApiModelProperty(value = "输入码")
    private String srm;

    /**
     * 父级分中心id
     */
    @ApiModelProperty(value = "父级分中心id")
    private String pid;

    /**
     * 是否删除：0.未删除 1.已删除
     */
    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 只能有一个为1的分中心,当前数据库的分中心
     */
    @ApiModelProperty(value = "只能有一个为1的分中心,当前数据库的分中心")
    private Integer isDefault;

    /**
     * 机构类型，详见：com.center.medical.bean.enums
     */
    @ApiModelProperty(value = "机构类型，详见：com.center.medical.bean.enums")
    private Integer branchType;

    /**
     * 是否支持在线支付：0.否 1.是
     */
    @ApiModelProperty(value = "是否支持在线支付：0.否 1.是")
    private Integer fPayOnline;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lng;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lat;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String tel;

    /**
     * 抽号开始时间1 HH:mm
     */
    @ApiModelProperty(value = "抽号开始时间1 HH:mm")
    private String startTime;

    /**
     * 抽号结束时间1 HH:mm
     */
    @ApiModelProperty(value = "抽号结束时间1 HH:mm")
    private String endTime;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 检前须知
     */
    @ApiModelProperty(value = "检前须知")
    private String inspectInfo;

    /**
     * 抽号须知
     */
    @ApiModelProperty(value = "抽号须知")
    private String noInfo;

    /**
     * 机构照片
     */
    @ApiModelProperty(value = "机构照片")
    private String pics;

    /**
     * 机构介绍
     */
    @ApiModelProperty(value = "机构介绍")
    private String introduce;

    /**
     * 前台须知
     */
    @ApiModelProperty(value = "前台须知")
    private String qtInfo;

    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String picture;

    /**
     * 机构门店id
     */
    @ApiModelProperty(value = "机构门店id")
    private String hospitalSubId;

    /**
     * 拼音简码（用于会员卡卡号）
     */
    @ApiModelProperty(value = "拼音简码（用于会员卡卡号）")
    private String pyjm;

    /**
     * 是否展示：0.否 1.是
     */
    @ApiModelProperty(value = "是否展示：0.否 1.是")
    private Integer isShow;

    @ApiModelProperty(value = "是否需要预约：0.否 1.是")
    private Integer needReservation;

    /**
     * 抽号开始时间2 HH:mm
     */
    @ApiModelProperty(value = "抽号开始时间2 HH:mm")
    private String startTime2;

    /**
     * 抽号结束时间2 HH:mm
     */
    @ApiModelProperty(value = "抽号结束时间2 HH:mm")
    private String endTime2;

    /**
     * 预约周期(天)
     */
    @ApiModelProperty(value = "预约周期(天)")
    private Integer reservationPeriod;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer branchSort;

    /**
     * 所属组织
     */
    @ApiModelProperty(value = "所属组织")
    private String centerorgname;

    /**
     * 分钟中心简介
     */
    @ApiModelProperty(value = "分钟中心简介")
    private String briefFzx;

    /**
     * isWechatApp
     */
    @ApiModelProperty(value = "isWechatApp")
    private Integer isWechatApp;

    /**
     * 队列地址
     */
    @ApiModelProperty(value = "队列地址")
    private String queueUrl;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latGcj;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lngGcj;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}

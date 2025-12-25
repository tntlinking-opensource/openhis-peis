package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 分中心维护表(SysBranch)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
@Data
@TableName("sys_branch")
@ApiModel(value = "SysBranch", description = "分中心维护表实体类")
public class SysBranch extends Model<SysBranch> implements Serializable {
    private static final long serialVersionUID = 124879468858184515L;

    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "分中心id")
    private String branchId;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    @ApiModelProperty(value = "简码，不能重复")
    private String jm;

    @ApiModelProperty(value = "输入码")
    private String srm;

    @ApiModelProperty(value = "父级分中心id")
    private String pid;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "只能有一个为1的分中心,当前数据库的分中心")
    private Integer isDefault;

    @ApiModelProperty(value = "机构类型，详见：com.world.center.bean.enums")
    private Integer branchType;

    @ApiModelProperty(value = "是否支持在线支付：0.否 1.是")
    private Integer fPayOnline;

    @ApiModelProperty(value = "经度")
    private String lng;

    @ApiModelProperty(value = "纬度")
    private String lat;

    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ApiModelProperty(value = "抽号开始时间1 HH:mm")
    private String startTime;

    @ApiModelProperty(value = "抽号结束时间1 HH:mm")
    private String endTime;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "检前须知")
    private String inspectInfo;

    @ApiModelProperty(value = "抽号须知")
    private String noInfo;

    @ApiModelProperty(value = "机构照片")
    private String pics;

    @ApiModelProperty(value = "机构介绍")
    private String introduce;

    @ApiModelProperty(value = "前台须知")
    private String qtInfo;

    @ApiModelProperty(value = "缩略图")
    private String picture;

    @ApiModelProperty(value = "机构门店id")
    private String hospitalSubId;

    @ApiModelProperty(value = "拼音简码（用于会员卡卡号）")
    private String pyjm;

    @ApiModelProperty(value = "是否展示：0.否 1.是")
    private Integer isShow;

    @ApiModelProperty(value = "是否需要预约：0.否 1.是")
    private Integer needReservation;

    @ApiModelProperty(value = "抽号开始时间2 HH:mm")
    private String startTime2;

    @ApiModelProperty(value = "抽号结束时间2 HH:mm")
    private String endTime2;

    @ApiModelProperty(value = "预约周期(天)")
    private Integer reservationPeriod;

    @ApiModelProperty(value = "排序")
    private Integer branchSort;

    @ApiModelProperty(value = "所属组织")
    private String centerorgname;

    @ApiModelProperty(value = "brief_fzx")
    private String briefFzx;

    @ApiModelProperty(value = "is_wechat_app")
    private Integer isWechatApp;

    @ApiModelProperty(value = "队列地址")
    private String queueUrl;

    @ApiModelProperty(value = "纬度")
    private String latGcj;

    @ApiModelProperty(value = "经度")
    private String lngGcj;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}

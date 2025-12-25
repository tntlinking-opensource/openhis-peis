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
 * 客户公共池(CrmClientcommon)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
@Data
@TableName("crm_clientcommon")
@ApiModel(value = "CrmClientcommon", description = "客户公共池实体类")
public class CrmClientcommon extends Model<CrmClientcommon> implements Serializable {
    private static final long serialVersionUID = 299157428286639087L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @ApiModelProperty(value = "客户电话")
    private String khdh;

    @ApiModelProperty(value = "所属行业")
    private String sshy;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "单位机构代码")
    private String dwjgdm;

    @ApiModelProperty(value = "客户单位地址")
    private String khdwdz;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "领取次数统计")
    private Integer lqcstj;

    @ApiModelProperty(value = "分配状态：0.未分配 1.已分配")
    private String fpzt;

    @ApiModelProperty(value = "法人单位名称")
    private String frdwmc;

    @ApiModelProperty(value = "法定代表人")
    private String fddbr;

    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    @ApiModelProperty(value = "企业规模")
    private String qygm;

    @ApiModelProperty(value = "企业经济类型")
    private String qyjjlx;

    @ApiModelProperty(value = "职业卫生负责人")
    private String zywsfzr;

    @ApiModelProperty(value = "客户单位注册地址")
    private String khdwzcdz;

    @ApiModelProperty(value = "职业卫生管理机构")
    private String zywsgljg;

    @ApiModelProperty(value = "注册类型")
    private String zclx;

    @ApiModelProperty(value = "隶属关系")
    private String lsgx;

    @ApiModelProperty(value = "上级主管单位")
    private String sjzgdw;

    @ApiModelProperty(value = "实际从业人数")
    private String sjcyrs;

    @ApiModelProperty(value = "流动人数")
    private String ldrs;

    @ApiModelProperty(value = "生产工人数")
    private String scgrs;

    @ApiModelProperty(value = "客户上次体检单位地址")
    private String khsctjdwdz;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

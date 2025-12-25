package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户公共池表(Clientcommon)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:56
 */
@Data
@TableName("crm_clientcommon")
@ApiModel(value = "Clientcommon", description = "客户公共池表实体类")
public class Clientcommon extends Model<Clientcommon> implements Serializable {
    private static final long serialVersionUID = 884970412177967298L;

    @TableField(exist = false)
    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rowNum;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "客户单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "客户单位输入码")
    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @Excel(name = "客户单位联系人")
    @ApiModelProperty(value = "客户单位联系人")
    private String khdwlxr;

    @Excel(name = "客户电话")
    @ApiModelProperty(value = "客户电话")
    private String khdh;

    @Excel(name = "所属行业")
    @ApiModelProperty(value = "所属行业")
    private String sshy;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "单位机构代码")
    @ApiModelProperty(value = "单位机构代码")
    private String dwjgdm;

    @Excel(name = "客户单位地址")
    @ApiModelProperty(value = "客户单位地址")
    private String khdwdz;

    @Excel(name = "分中心ID")
    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @Excel(name = "销售经理ID")
    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @Excel(name = "领取次数统计")
    @ApiModelProperty(value = "领取次数统计")
    private Integer lqcstj;

    @Excel(name = "分配状态：0.未分配 1.已分配")
    @ApiModelProperty(value = "分配状态：0.未分配 1.已分配")
    private String fpzt;

    @Excel(name = "法人单位名称")
    @ApiModelProperty(value = "法人单位名称")
    private String frdwmc;

    @Excel(name = "法定代表人")
    @ApiModelProperty(value = "法定代表人")
    private String fddbr;

    @Excel(name = "邮政编码")
    @ApiModelProperty(value = "邮政编码")
    private String yzbm;

    @Excel(name = "企业规模")
    @ApiModelProperty(value = "企业规模")
    private String qygm;

    @Excel(name = "企业经济类型")
    @ApiModelProperty(value = "企业经济类型")
    private String qyjjlx;

    @Excel(name = "职业卫生负责人")
    @ApiModelProperty(value = "职业卫生负责人")
    private String zywsfzr;

    @Excel(name = "客户单位注册地址")
    @ApiModelProperty(value = "客户单位注册地址")
    private String khdwzcdz;

    @Excel(name = "职业卫生管理机构")
    @ApiModelProperty(value = "职业卫生管理机构")
    private String zywsgljg;

    @Excel(name = "注册类型")
    @ApiModelProperty(value = "注册类型")
    private String zclx;

    @Excel(name = "隶属关系")
    @ApiModelProperty(value = "隶属关系")
    private String lsgx;

    @Excel(name = "上级主管单位")
    @ApiModelProperty(value = "上级主管单位")
    private String sjzgdw;

    @Excel(name = "实际从业人数")
    @ApiModelProperty(value = "实际从业人数")
    private String sjcyrs;

    @Excel(name = "流动人数")
    @ApiModelProperty(value = "流动人数")
    private String ldrs;

    @Excel(name = "生产工人数")
    @ApiModelProperty(value = "生产工人数")
    private String scgrs;

    @Excel(name = "客户上次体检单位地址")
    @ApiModelProperty(value = "客户上次体检单位地址")
    private String khsctjdwdz;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @Excel(name = "是否删除：0.未删除 1.已删除")
    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @Excel(name = "创建时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @Excel(name = "修改日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;
}

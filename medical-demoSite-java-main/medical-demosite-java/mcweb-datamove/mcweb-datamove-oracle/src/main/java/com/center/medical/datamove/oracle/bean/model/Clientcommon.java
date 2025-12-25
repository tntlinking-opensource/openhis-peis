package com.center.medical.datamove.oracle.bean.model;


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
 * 客户公共池表(Clientcommon)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:17:38
 */
@Data
@TableName("CLIENTCOMMON")
@ApiModel(value = "Clientcommon", description = "客户公共池表实体类")
public class Clientcommon extends Model<Clientcommon> implements Serializable {
    private static final long serialVersionUID = -11486343563018724L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
    private String lqcstj;

    @ApiModelProperty(value = "0:存在    1:删除   2:正式")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "0: 还可领取 1:不可领取")
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

    @ApiModelProperty(value = "职业病危害因素人数")
    private String zybwhysrs;

    @ApiModelProperty(value = "职业病危害作业场所数")
    private String zybwhzycss;

    @ApiModelProperty(value = "职业病危害因素类别")
    private String zybwhyslb;

    @ApiModelProperty(value = "职业病危害因素")
    private String zybwhys;

    @ApiModelProperty(value = "工艺流程")
    private String gylc;

    @ApiModelProperty(value = "主要原辅料")
    private String zyyfl;

    @ApiModelProperty(value = "体检团体类型")
    private Integer tjttlx;

    @ApiModelProperty(value = "主要产品")
    private String zycp;

    @ApiModelProperty(value = "客户上次体检单位地址")
    private String khsctjdwdz;

    @ApiModelProperty(value = "备注")
    private String bz;
}

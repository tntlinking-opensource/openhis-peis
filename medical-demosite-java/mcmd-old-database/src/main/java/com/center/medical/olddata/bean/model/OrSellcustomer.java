package com.center.medical.olddata.bean.model;


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
 * 我的客户表(Sellcustomer)表实体类
 *
 * @author ay
 * @since 2023-07-25 21:00:59
 */
@Data
@TableName("SELLCUSTOMER")
@ApiModel(value = "Sellcustomer", description = "我的客户表实体类")
public class OrSellcustomer extends Model<OrSellcustomer> implements Serializable {
    private static final long serialVersionUID = -58194365858849668L;

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

    @ApiModelProperty(value = "单位机构代码")
    private String dwjgdm;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "注册类型")
    private String zclx;

    @ApiModelProperty(value = "所属行业")
    private String sshy;

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

    @ApiModelProperty(value = "升级日期")
    private Date sjrq;

    @ApiModelProperty(value = "0：潜在 1：正式 2：释放")
    private String khzt;

    @ApiModelProperty(value = "0：显示 1：假删")
    private Integer isDelete;

    @ApiModelProperty(value = "客户公共池领取ID")
    private String clientid;

    @ApiModelProperty(value = "领导分配ID")
    private String ldfpid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "流失标志（1、已流失）")
    private Integer lost;

    @ApiModelProperty(value = "${column.comment}")
    private Double intId;

    @ApiModelProperty(value = "${column.comment}")
    private String briefText;

    @ApiModelProperty(value = "${column.comment}")
    private String socialCreditCode;

    @ApiModelProperty(value = "${column.comment}")
    private String province;

    @ApiModelProperty(value = "${column.comment}")
    private String city;

    @ApiModelProperty(value = "${column.comment}")
    private String district;

    @ApiModelProperty(value = "${column.comment}")
    private String street;

    @ApiModelProperty(value = "${column.comment}")
    private String indusTypeCode;

    @ApiModelProperty(value = "${column.comment}")
    private String economyCode;

    @ApiModelProperty(value = "${column.comment}")
    private String crptSizeCode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer workForce;

    @ApiModelProperty(value = "${column.comment}")
    private Integer holdCardMan;

    @ApiModelProperty(value = "${column.comment}")
    private Integer workmanNum;

    @ApiModelProperty(value = "${column.comment}")
    private Integer workmistressNum;

    @ApiModelProperty(value = "${column.comment}")
    private String workArea;

    @ApiModelProperty(value = "${column.comment}")
    private String registerFund;

    @ApiModelProperty(value = "${column.comment}")
    private String safetyPrincipal;

    @ApiModelProperty(value = "${column.comment}")
    private Date buildDate;

    @ApiModelProperty(value = "${column.comment}")
    private String linkman1;

    @ApiModelProperty(value = "${column.comment}")
    private String position1;

    @ApiModelProperty(value = "${column.comment}")
    private String linkphone1;

    @ApiModelProperty(value = "${column.comment}")
    private String linkman2;

    @ApiModelProperty(value = "${column.comment}")
    private String position2;

    @ApiModelProperty(value = "${column.comment}")
    private String linkphone2;

    @ApiModelProperty(value = "${column.comment}")
    private String safeposition;

    @ApiModelProperty(value = "${column.comment}")
    private String safephone;

    @ApiModelProperty(value = "${column.comment}")
    private String subjeConn;

    @ApiModelProperty(value = "${column.comment}")
    private String enrolAddress;

    @ApiModelProperty(value = "${column.comment}")
    private String enrolPostalcode;

    @ApiModelProperty(value = "${column.comment}")
    private String occManaOffice;

    @ApiModelProperty(value = "${column.comment}")
    private Integer jinanStatus;

    @ApiModelProperty(value = "${column.comment}")
    private String jinanMsg;

    @ApiModelProperty(value = "${column.comment}")
    private String indusTypeCode1;

    @ApiModelProperty(value = "${column.comment}")
    private String indusTypeCode2;

    @ApiModelProperty(value = "${column.comment}")
    private String indusTypeCode3;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private String rauKhdwmc;

    @ApiModelProperty(value = "${column.comment}")
    private String rauSocialCreditCode;

    @ApiModelProperty(value = "${column.comment}")
    private String rauEconomyCode;

    @ApiModelProperty(value = "${column.comment}")
    private String rauIndusTypeCode1;

    @ApiModelProperty(value = "${column.comment}")
    private String rauIndusTypeCode2;

    @ApiModelProperty(value = "${column.comment}")
    private String rauIndusTypeCode3;

    @ApiModelProperty(value = "${column.comment}")
    private String rauIndusTypeCode;

    @ApiModelProperty(value = "${column.comment}")
    private String rauQygm;

    @ApiModelProperty(value = "${column.comment}")
    private String rauProvince;

    @ApiModelProperty(value = "${column.comment}")
    private String rauCity;

    @ApiModelProperty(value = "${column.comment}")
    private String rauDistrict;

    @ApiModelProperty(value = "${column.comment}")
    private String rauStreet;

    @ApiModelProperty(value = "${column.comment}")
    private String unitarea;

    @ApiModelProperty(value = "${column.comment}")
    private String rauUnitarea;

    @ApiModelProperty(value = "${column.comment}")
    private String licenseName;

    @ApiModelProperty(value = "${column.comment}")
    private String jindieId;
}

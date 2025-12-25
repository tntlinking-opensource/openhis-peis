package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.Hfclzt;
import com.center.medical.bean.enums.Hffffs;
import com.center.medical.bean.model.Peispatient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 高危人员管理表(Riskclient)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
@Data
@TableName("md_riskclient")
@ApiModel(value = "Riskclient", description = "高危人员管理表实体类")
public class Riskclient extends Model<Riskclient> implements Serializable {
    private static final long serialVersionUID = -51903403035300699L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String tjid;

    @ApiModelProperty(value = "高危人员名称")
    private String gwrymc;

    @ApiModelProperty(value = "年龄")
    private String nl;

    @ApiModelProperty(value = "性别：0.男 1.女 2.通用")
    private Integer xb;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "高危项目")
    private String gwxm;

    /**
     * @see ExamType
     */
    @ApiModelProperty(value = "体检类型：0.健康体检 1.职业体检 2.综合 3.复查")
    private Integer tjlx;

    @ApiModelProperty(value = "体检日期")
    private Date tirq;

    @ApiModelProperty(value = "状态：0：未处理1：处理")
    private Integer tjzt;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "分中心ID")
    private String cid;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "销售经理（开单医生ID）")
    private String idOpendoctor;

    @ApiModelProperty(value = "提报者")
    private String reportMan;

    @ApiModelProperty(value = "提报科室")
    private String reportDivision;

    @ApiModelProperty(value = "处理人")
    private String dealMan;

    @ApiModelProperty(value = "提报状态")
    private Integer reportstatus;

    @ApiModelProperty(value = "提报时间")
    private Date reportTime;

    @ApiModelProperty(value = "处理时间")
    private Date dealTime;

    @ApiModelProperty(value = "同步标志  1： 已同步  ")
    private Integer tbbz;

    @ApiModelProperty(value = "处理标志 科室-危急值提报-处理  1已处理  null未处理（弃用）")
    private Integer clbz;

    @ApiModelProperty(value = "业务处理人用户名")
    private String ywclr;

    @ApiModelProperty(value = "业务处理时间")
    private Date ywclsj;

    @ApiModelProperty(value = "业务处理选择的报告发放方式id")
    private String ywfffs;

    @ApiModelProperty(value = "业务处理状态")
    private Integer ywclzt;

    @ApiModelProperty(value = "业务处理备注")
    private String ywbz;

    @ApiModelProperty(value = "回访处理人用户名")
    private String hfclr;

    @ApiModelProperty(value = "回访处理时间")
    private Date hfclsj;

    /**
     * @see Hfclzt
     */
    @ApiModelProperty(value = "回访处理状态：0.已提报 1.继续提报 2.已完成 3.已取消")
    private Integer hfclzt;

    /**
     * @see Hffffs
     */
    @ApiModelProperty(value = "回访处理方式：0.当面告知 1.电话通知 2.短信通知")
    private String hffffs;

    @ApiModelProperty(value = "回访备注")
    private String hfbz;

    @ApiModelProperty(value = "回访发放方式")
    private Integer hfclfs;

    @ApiModelProperty(value = "专家处理人用户名")
    private String zjclr;

    @ApiModelProperty(value = "专家处理时间")
    private Date zjclsj;

    @ApiModelProperty(value = "专家处理状态 0未处理  1 已处理")
    private Integer zjclzt;

    @ApiModelProperty(value = "专家处理备注")
    private String zjbz;

    @ApiModelProperty(value = "专家处理方式")
    private Integer zjclfs;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @TableField(exist = false)
    @ApiModelProperty(value = "提报科室")
    private String deptName;

    @TableField(exist = false)
    @ApiModelProperty(value = "关联的高危项目")
    private List<Riskclientcon> riskclientconList;

    @TableField(exist = false)
    @ApiModelProperty(value = "体检者信息")
    private Peispatient patient;
}

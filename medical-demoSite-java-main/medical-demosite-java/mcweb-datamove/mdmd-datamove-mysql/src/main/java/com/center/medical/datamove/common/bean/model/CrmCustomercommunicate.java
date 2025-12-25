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
 * 客户沟通表(CrmCustomercommunicate)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
@Data
@TableName("crm_customercommunicate")
@ApiModel(value = "CrmCustomercommunicate", description = "客户沟通表实体类")
public class CrmCustomercommunicate extends Model<CrmCustomercommunicate> implements Serializable {
    private static final long serialVersionUID = 744183024050121204L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户预检跟踪ID")
    private String khdwmc;

    @ApiModelProperty(value = "客户联系电话")
    private String khlxdh;

    @ApiModelProperty(value = "上次体检开始日期")
    private Date sctjksrq;

    @ApiModelProperty(value = "沟通结果")
    private String gtjg;

    @ApiModelProperty(value = "沟通日期")
    private Date gtrq;

    @ApiModelProperty(value = "下次沟通日期")
    private Date xcgtrq;

    @ApiModelProperty(value = "本次沟通方式：0.电话 1.QQ 2.面对面 3.其它")
    private Integer bcgtfs;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;
}

package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.Bcgtfs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户沟通表(Customercommunicate)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:49
 */
@Data
@TableName("crm_customercommunicate")
@ApiModel(value = "Customercommunicate", description = "客户沟通表实体类")
public class Customercommunicate extends Model<Customercommunicate> implements Serializable {
    private static final long serialVersionUID = -61083258777683793L;

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

    /**
     * @see Bcgtfs
     */
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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "提交时间")
    private Date bcgtrq;


}

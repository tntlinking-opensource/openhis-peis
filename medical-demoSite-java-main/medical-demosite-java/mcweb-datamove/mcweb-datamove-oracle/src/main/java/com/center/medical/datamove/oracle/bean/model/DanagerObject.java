package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TJ危害因素收费项目(DanagerObject)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:07
 */
@Data
@TableName("DANAGER_OBJECT")
@ApiModel(value = "DanagerObject", description = "TJ危害因素收费项目实体类")
public class DanagerObject extends Model<DanagerObject> implements Serializable {
    private static final long serialVersionUID = -83268748064688789L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "危害因素分类名称")
    private String harmTypeName;

    @ApiModelProperty(value = "危害因素分类ID")
    private String harmTypeId;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素名称ID")
    private String harmId;

    @ApiModelProperty(value = "收费项目名称")
    private String items;

    @ApiModelProperty(value = "团体ID")
    private String sellcustomerId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "应查人数")
    private String peopleNum;

    @ApiModelProperty(value = "实查人数")
    private String inspectNum;

    @ApiModelProperty(value = "未查人数")
    private String unexploredNum;

    @ApiModelProperty(value = "男性人数")
    private String manNum;

    @ApiModelProperty(value = "女性人数")
    private String womenNum;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "上岗类型")
    private Integer onjobType;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;
}

package com.center.medical.bean.model;

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
 * TJ危害因素收费项目(DanagerObject)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:57
 */
@Data
@TableName("md_danager_object")
@ApiModel(value = "DanagerObject", description = "TJ危害因素收费项目实体类")
public class DanagerObject extends Model<DanagerObject> implements Serializable {
    private static final long serialVersionUID = -82382573586869236L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    private Integer peopleNum;

    @ApiModelProperty(value = "实查人数")
    private Integer inspectNum;

    @ApiModelProperty(value = "未查人数")
    private Integer unexploredNum;

    @ApiModelProperty(value = "男性人数")
    private Integer manNum;

    @ApiModelProperty(value = "女性人数")
    private Integer womenNum;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "上岗类型，详见：OnjobType")
    private String onjobType;

    @ApiModelProperty(value = "样本ID")
    private String sampleId;

    public DanagerObject(String harmTypeName, String harmTypeId, String harmName, String harmId, String items, String sellcustomerId, String orderNo, String onjobType
            , String sampleId, Integer peopleNum) {
        super();
        this.harmTypeName = harmTypeName;
        this.harmTypeId = harmTypeId;
        this.harmName = harmName;
        this.harmId = harmId;
        this.items = items;
        this.sellcustomerId = sellcustomerId;
        this.orderNo = orderNo;
        this.onjobType = onjobType;
        this.sampleId = sampleId;
        this.peopleNum = peopleNum;
        this.inspectNum = 0;
        this.unexploredNum = 0;
        this.manNum = 0;
        this.womenNum = 0;
    }
}

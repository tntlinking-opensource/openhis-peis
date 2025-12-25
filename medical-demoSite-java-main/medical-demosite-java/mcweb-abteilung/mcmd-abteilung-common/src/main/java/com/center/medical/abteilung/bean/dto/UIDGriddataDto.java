package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 补检保存数据
 */
@Data
public class UIDGriddataDto implements Serializable {
    private static final long serialVersionUID = 5837186496764552149L;

    @ApiModelProperty(value = "登记员ID")
    private String idDoctorreg;

    @ApiModelProperty(value = "性别")
    private String FFeechargedinttrans;

    @ApiModelProperty(value = "收费项目ID")
    private String idExamfeeitem;

    @ApiModelProperty(value = "个数")
    private String count;

    @ApiModelProperty(value = "弃检 0 或者null：未弃检 1：弃检")
    private String FGiveup;

    @ApiModelProperty(value = "换项（相当于删除本项的标记）退项")
    private String changeItem;

    @ApiModelProperty(value = "实付价格")
    private String factprice;

    @ApiModelProperty(value = "是否是最小套餐：0不是 1是")
    private String isMintc;

    @ApiModelProperty(value = "分组id")
    private String idOrgreservationgroup;

    @ApiModelProperty(value = "分组名称")
    private String orgreservationgroupname;

    @ApiModelProperty(value = "迟检")
    private String FDelayed;

    @ApiModelProperty(value = "收费检查项目名称")
    private String examfeeitemName;


    @ApiModelProperty(value = "是否备选：0不是 1是")
    private Integer isbx;

    @ApiModelProperty(value = "id科室")
    private String idKs;

    @ApiModelProperty(value = "是否加项")
    private String sfjx;

    @ApiModelProperty(value = "登记员(冗余)")
    private String doctorregR;


    @ApiModelProperty(value = "原始价格")
    private String price;

    @ApiModelProperty(value = "备选数量")
    private Integer bxcount;

    @ApiModelProperty(value = "序号")
    private Integer qty;


    @ApiModelProperty(value = "zybj")
    private Integer zybj;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "uid")
    private Integer uid;

    @ApiModelProperty(value = "_id")
    private String id;

    @ApiModelProperty(value = "状态，removed删除，modified修改，added添加")
    private String state;

    @ApiModelProperty(value = "补检状态 0: 未补检 1：已补检")
    private Integer fTransferedhl7;

}

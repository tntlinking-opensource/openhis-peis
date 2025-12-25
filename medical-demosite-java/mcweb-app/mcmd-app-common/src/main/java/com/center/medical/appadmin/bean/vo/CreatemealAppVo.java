package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 小程序套餐表分页返回数据
 */
@Data
public class CreatemealAppVo implements Serializable {
    private static final long serialVersionUID = -4553234453485369406L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "类型id")
    private String type;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "状态 -1下线, 0待处理 ,1上线 ")
    private Integer status;

    @ApiModelProperty(value = "顺序")
    private Integer seq;

    @ApiModelProperty(value = "标签名,用英文逗号隔开")
    private String label;

    @ApiModelProperty(value = "体检系统套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "套餐原始价格")
    private Double tcysjg;


    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是 (禁用给他标红一下吧，禁用的小程序不显示)")
    private Integer isBan;

    @ApiModelProperty(value = "分中心名称")
    private String fzxName;

    @ApiModelProperty(value = "分中心")
    private String fzxId;

    @ApiModelProperty(value = "分中心")
    private String appTcmc;
}

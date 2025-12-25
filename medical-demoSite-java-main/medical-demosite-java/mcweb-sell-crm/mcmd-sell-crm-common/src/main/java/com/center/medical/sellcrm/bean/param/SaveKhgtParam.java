package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存客户沟通记录参数
 */
@Data
public class SaveKhgtParam implements Serializable {
    private static final long serialVersionUID = 757780040845274238L;


    @ApiModelProperty(value = "id,客户预检跟踪表id")
    private String id;

    @ApiModelProperty(value = "下次沟通日期")
    private String xcgtrq;

    @ApiModelProperty(value = "沟通结果")
    private String gtjg;

    @ApiModelProperty(value = "上次体检开始日期")
    private String sctjksrq;

    @ApiModelProperty(value = "提交时间")
    private String bcgtrq;

    @ApiModelProperty(value = "销售经理id")
    private String xsjl;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "本次沟通方式：0.电话 1.QQ 2.面对面 3.其它")
    private Integer bcgtfs;

}

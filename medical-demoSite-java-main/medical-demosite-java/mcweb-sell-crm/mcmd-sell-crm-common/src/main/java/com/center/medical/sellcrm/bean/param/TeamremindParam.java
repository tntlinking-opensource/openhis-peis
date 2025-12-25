package com.center.medical.sellcrm.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeamremindParam extends BaseParam implements Serializable {

    private static final long serialVersionUID = -6494343949574523748L;

    @ApiModelProperty(value = "来检状态(0,未来检，1，已来检)")
    private String isExamed;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户联系电话")
    private String khlxdh;

    @ApiModelProperty(value = "处理人")
    private String clr;

    @ApiModelProperty(value = "上次体检开始日期")
    private String sctjksrq;

    @ApiModelProperty(value = "下次沟通日期")
    private String xcgtrq;

    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;

    @ApiModelProperty(value = "分中心id")
    private String fzxid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "今天的日期")
    private Date today;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "参数monthText")
    private Integer monthText;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "参数size")
    private Integer size;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "手动设置的上次体检开始日期")
    private String setSctjksrq;

}

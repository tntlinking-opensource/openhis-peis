package com.center.medical.sellcrm.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户跟进 参数
 */
@Data
public class SaveCuParam implements Serializable {
    private static final long serialVersionUID = 339882435997744678L;

    @ApiModelProperty(value = "团体ID")
    private String khdwmcid;

    @ApiModelProperty(value = "主题")
    private String zt;

    @ApiModelProperty(value = "客户负责人")
    private String khfzr;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "跟进日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gjrq;

    @ApiModelProperty(value = "客户跟踪阶段 0.需求交流 1.方案谈判 2.价格谈判 3.体检确认 4.合同签订")
    private Integer gjjd;

    @ApiModelProperty(value = "结束阶段,true或false")
    private String jdjs;

    @ApiModelProperty(value = "跟进内容")
    private String gjnr;


    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

}

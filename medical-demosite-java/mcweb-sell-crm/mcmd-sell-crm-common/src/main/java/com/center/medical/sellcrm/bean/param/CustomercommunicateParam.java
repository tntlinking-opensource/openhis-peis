package com.center.medical.sellcrm.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomercommunicateParam implements Serializable {


    private static final long serialVersionUID = 8009757365622318168L;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "客户联系电话")
    private String khlxdh;

    @ApiModelProperty(value = "沟通日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gtrq;

    @ApiModelProperty(value = "下次沟通日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date xcgtrq;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "销售经理id")
    private String xsjlid;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心")
    private String fzxid;

}

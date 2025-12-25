package com.center.medical.center.qingdao.profession.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WfjkDto {


    @ApiModelProperty(value = "给第三方分配的应用标识")
    private String appId;

    @ApiModelProperty(value = "当前时间,格式：yyyyMMddHHmmss;示例：20230808100727")
    private String once;

    @ApiModelProperty(value = "上传批次号,相同批次号会把数据合并到同一个任务中；示例：1，2，3，4")
    private String seq;

    @ApiModelProperty(value = "true:结束；false:未结束；调用接口上传完同一个批次的数据后再结束，比如要上传 100 个报告，在最后一次调用接口的时候设置 seqEndFlag=true")
    private Boolean seqEndFlag;


    @ApiModelProperty(value = "签验串 对 appId、once 及 content 字段 JSON进行签名，签名算法参考附录 3.1")
    private String sign;


    @ApiModelProperty(value = "入参对象 体检报告的加密值，加密方法加 3.2")
    private String content;


}

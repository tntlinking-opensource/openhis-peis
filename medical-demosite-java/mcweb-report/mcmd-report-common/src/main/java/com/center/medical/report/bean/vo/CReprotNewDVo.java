package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 综合分析-生成报告
 */
@Data
public class CReprotNewDVo implements Serializable {
    private static final long serialVersionUID = -5182814931382840716L;


    /**
     * 健康
     */
    @ApiModelProperty(value = "模板后缀1_1数据")
    private Map<String, Object> data1_1;

    @ApiModelProperty(value = "模板后缀1_2数据")
    private Map<String, Object> data1_2;

    @ApiModelProperty(value = "模板后缀1_3数据")
    private Map<String, Object> data1_3;

    @ApiModelProperty(value = "模板后缀2_1数据")
    private Map<String, Object> data2_1;

    @ApiModelProperty(value = "模板后缀2_2数据")
    private Map<String, Object> data2_2;

    @ApiModelProperty(value = "模板后缀3_0数据,3_1和3_0整合到一起了,这个key就是DEPNAME")
    private List<Map<String,Object>> data3_0;

    @ApiModelProperty(value = "模板后缀3_1数据,3_1和3_0整合到一起了")
    private Map<String, Object> data3_1;

    @ApiModelProperty(value = "模板后缀3_1数据")
    private List<Map> data4_2;


    /**
     * 职业
     */
    @ApiModelProperty(value = "模板template_0数据")
    private Map<String, Object> template_0;

    @ApiModelProperty(value = "模板template_1数据")
    private Map<String, Object> template_1;

    @ApiModelProperty(value = "模板template_2数据")
    private Map<String, Object> template_2;

    @ApiModelProperty(value = "模板list_1数据")
    private Map<String, Object> list_1;

    @ApiModelProperty(value = "模板list_2数据")
    private Map<String, Object> list_2;

    @ApiModelProperty(value = "模板list_3数据")
    private Map<String, Object> list_3;

    @ApiModelProperty(value = "模板list_4数据")
    private Map<String, Object> list_4;

    @ApiModelProperty(value = "模板list_5数据")
    private Map<String, Object> list_5;

    @ApiModelProperty(value = "模板list_6数据")
    private Map<String, Object> list_6;

    @ApiModelProperty(value = "模板endpage数据")
    private Map<String, Object> endpage;


    @ApiModelProperty(value = "分中心设置")
    private ReportConfigVo branchConfig;


    @ApiModelProperty(value = "生成的word的地址（临时文件，未上传）")
    private String wordUrl;

    @ApiModelProperty(value = "生成的pdf的地址（临时文件，未上传）")
    private String pdfUrl;

}

package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-11-23 17:27
 * @description: 查询报告列表数据参数
 */
@Data
public class ImgJkParam implements Serializable {

    private static final long serialVersionUID = 0;
    
    @ApiModelProperty(value = "ID")
    private String reportId;

    @ApiModelProperty(value = "参检人员构成情况图（男）")
    private String pic_inspect_m;

    @ApiModelProperty(value = "参检人员构成情况图(女）")
    private String pic_inspect_w;

    @ApiModelProperty(value = "参检人员构成情况图（综合）")
    private String pic_inspect_t;

    @ApiModelProperty(value = "男女合计各年龄段构成（柱型）")
    private String pic_age_columnar;

    @ApiModelProperty(value = "男女合计各年龄段构成（饼图）")
    private String pic_age_pie;

    @ApiModelProperty(value = "女合计各年龄段构成（饼图）")
    private String pic_age_w;

    @ApiModelProperty(value = "男合计各年龄段构成（饼图）")
    private String pic_age_m;

    @ApiModelProperty(value = "横坐标为在上表中各体检项目的行号")
    private String item_total;

    @ApiModelProperty(value = "男性前十大异常结果(单位:人)")
    private String exception_m;

    @ApiModelProperty(value = "女性前十大异常结果(单位:人)")
    private String exception_w;

    @ApiModelProperty(value = "男女综合前十大异常结果(单位:人)")
    private String exception_t;

}
